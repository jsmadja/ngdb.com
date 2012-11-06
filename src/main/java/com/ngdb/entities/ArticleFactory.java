package com.ngdb.entities;

import com.google.common.base.Function;
import com.ngdb.Comparators;
import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tags;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Comparators.compareByNghOrId;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.hibernate.criterion.Projections.*;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.isNotEmpty;
import static org.hibernate.criterion.Restrictions.isNotNull;

public class ArticleFactory {

	@Inject
	private Session session;

	public Article findById(Long id) {
		return (Article) session.load(Article.class, id);
	}

    public Long getNumAccessories() {
        return (Long) session.createCriteria(Accessory.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public Long getNumHardwares() {
        return (Long) session.createCriteria(Hardware.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public List<Game> findAllGamesByNgh(String ngh) {
        return allGames().add(eq("ngh", ngh)).list();
    }

    public Collection<Game> findAllGamesLightByNgh(String ngh) {
        return transform(allGames().setProjection(lightProjection()).add(eq("ngh", ngh)).list(), toGame);
    }

    public long getNumGames() {
        return findAllGamesLight().size();
    }

    public List<Game> findAllGames() {
        return allGames().list();
    }

    public List<Game> findAllGamesWithReviews() {
        return allGames().add(isNotEmpty("reviews.reviews")).list();
    }

    public Collection<Game> findAllGamesLight() {
        List<Object[]> list = allGames().setProjection(lightProjection()).list();
        return transform(list, toGame);
    }

    private ProjectionList lightProjection() {
        ProjectionList projectionList = projectionList();
        for(String property: asList("id", "title", "originTitle", "platformShortName", "publisher", "coverUrl")) {
            projectionList.add(property(property));
        }
        return projectionList;
    }

    private Criteria allGames() {
        return session.createCriteria(Game.class).setCacheable(true);
    }

    public Game getRandomGameWithMainPicture() {
        List<Long> gamesWithMainPicture = session.createCriteria(Game.class).setProjection(id()).add(isNotNull("coverUrl")).add(eq("platformShortName", "AES")).setCacheable(true).list();
        int randomId = RandomUtils.nextInt(gamesWithMainPicture.size());
        Long randomGameId = gamesWithMainPicture.get(randomId);
        return (Game) session.load(Game.class, randomGameId);
    }

    private static Function<Object[], Game> toGame = new Function<Object[], Game>() {
        @Override
        public Game apply(@Nullable Object[] input) {
            Game game = new Game();
            game.setId((Long) input[0]);
            game.setTitle(input[1].toString());
            game.setOriginTitle(input[2].toString());
            game.setPlatformShortName(input[3].toString());
            game.setPublisher((Publisher) input[4]);
            if(input[5] != null) {
                game.setCover(input[5].toString());
            }
            return game;
        }
    };

    public Set<Review> findAllReviewOfNgh(String ngh) {
        String sql = "SELECT * FROM Review WHERE article_id IN (SELECT id FROM Game WHERE ngh = '" + ngh + "')";
        SQLQuery query = session.createSQLQuery(sql).addEntity(Review.class);
        return new TreeSet<Review>(query.list());
    }

    public List<Game> retrieveGamesToTag() {
        List<Game> games = findAllGames();
        sort(games, compareByNghOrId);
        String oldNgh = null;
        Tags oldTags = null;
        List<Game> nonConsistentTagGames = new ArrayList<Game>();
        for (Game game:games) {
            String currentNgh = game.getNgh();
            Tags currentTags = game.getTags();
            if(currentTags.isEmpty()) {
                nonConsistentTagGames.add(game);
            } else {
                if(oldNgh != null && currentNgh != null) {
                    if(oldNgh.equalsIgnoreCase(currentNgh)) {
                        if(!oldTags.isEqualTo(currentTags)) {
                            nonConsistentTagGames.add(game);
                        }
                    }
                } else {
                    nonConsistentTagGames.add(game);
                }
            }
            oldNgh = currentNgh;
            oldTags = currentTags;
        }
        return nonConsistentTagGames;
    }


}
