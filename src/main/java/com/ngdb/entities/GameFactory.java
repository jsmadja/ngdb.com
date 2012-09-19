package com.ngdb.entities;

import com.google.common.base.Function;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.user.User;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.transform;
import static java.util.Arrays.asList;
import static org.hibernate.criterion.Projections.id;
import static org.hibernate.criterion.Projections.projectionList;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.isNotNull;

@SuppressWarnings("unchecked")
public class GameFactory {

    @Inject
    private Session session;

    public List<Game> findAllByNgh(String ngh) {
        return allGames().add(eq("ngh", ngh)).list();
    }

    public Collection<Game> findAllByPlatform(Platform platform) {
        return allGames().add(eq("platformShortName", platform.getShortName())).list();
    }

    public long getNumGames() {
        return findAllLight().size();
    }

    public List<Game> findAll() {
        return allGames().list();
    }

    public Collection<Game> findAllLight() {
        ProjectionList projectionList = projectionList();
        for(String property: asList("id", "title", "originTitle", "platformShortName", "publisher", "coverUrl")) {
            projectionList.add(property(property));
        }
        List<Object[]> list = allGames().setProjection(projectionList).list();
        return transform(list, toGame);
    }

    private Criteria allGames() {
        return session.createCriteria(Game.class).setFetchSize(100).setCacheable(true);
    }

    public Game getRandomGameWithMainPicture() {
        List<Long> gamesWithMainPicture = session.createCriteria(Game.class).setProjection(id()).add(isNotNull("coverUrl")).add(eq("platformShortName", "AES")).setCacheable(true).list();
        int randomId = RandomUtils.nextInt(gamesWithMainPicture.size());
        Long randomGameId = gamesWithMainPicture.get(randomId);
        return (Game) session.load(Game.class, randomGameId);
    }

    public Collection<Game> findAllGamesOwnedBy(final User owner) {
        String sql = "SELECT id,title,origin_title,platform_short_name,cover_url FROM Game WHERE id IN (SELECT article_id FROM CollectionObject WHERE user_id = "+owner.getId()+")";
        List<Object[]> list = session.createSQLQuery(sql).list();
        return transform(list, new Function<Object[], Game>() {
            @Override
            public Game apply(@Nullable Object[] input) {
                Game game = new Game();
                game.setId(((BigInteger) input[0]).longValue());
                game.setTitle(input[1].toString());
                game.setOriginTitle(input[2].toString());
                game.setPlatformShortName(input[3].toString());
                if(input[4] != null) {
                    game.setCover(input[4].toString());
                }
                return game;
            }});
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

}
