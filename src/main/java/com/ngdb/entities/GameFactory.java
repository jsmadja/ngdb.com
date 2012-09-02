package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static org.hibernate.criterion.Projections.rowCount;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.user.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

    public Long getNumGames() {
        return (Long) session.createCriteria(Game.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public List<Game> findAll() {
        return allGames().list();
    }

    private Criteria allGames() {
        return session.createCriteria(Game.class).setCacheable(true);
    }

    public Game getRandomGameWithMainPicture() {
        List<Long> gamesWithMainPicture = session.createCriteria(Game.class).setProjection(Projections.id()).add(Restrictions.isNotNull("coverUrl")).setCacheable(true).list();
        int randomId = RandomUtils.nextInt(gamesWithMainPicture.size());
        Long randomGameId = gamesWithMainPicture.get(randomId);
        return (Game) session.load(Game.class, randomGameId);
    }

    public List<Article> findAllOwnedBy(final User owner) {
        return new ArrayList<Article>(filter(findAll(), new Predicate<Article>() {
            @Override
            public boolean apply(Article input) {
                return owner.owns(input);
            }
        }));
    }

}
