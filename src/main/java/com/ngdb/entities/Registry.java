package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;

import java.util.*;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.Predicates;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Tag;

public class Registry {

    @Inject
    private GameFactory gameFactory;

    @Inject
    private HardwareFactory hardwareFactory;

    @Inject
    private Session session;

    public List<Game> findGamesMatching(final String search) {
        List<Game> foundGames = new ArrayList<Game>();

        Set<Long> ids = new TreeSet<Long>();

        if (isNotBlank(search)) {
            final String searchItem = search.toLowerCase().trim();
            List<Game> allGames = gameFactory.findAll();
            Collection<Game> matchingGames = filter(allGames, new Predicates.Matching(searchItem));
            for (Article matchingArticle : matchingGames) {
                if(!ids.contains(matchingArticle.getId())) {
                    Game game = (Game) matchingArticle;
                    foundGames.add(game);
                    ids.add(game.getId());
                    List<Game> linkedGames = gameFactory.findAllByNgh(game.getNgh());
                    for (Game linkedGame : linkedGames) {
                        if(!ids.contains(linkedGame.getId())) {
                            foundGames.add(linkedGame);
                            ids.add(linkedGame.getId());
                        }
                    }
                }
            }
            sort(foundGames, byTitlePlatformOrigin);
        }
        return foundGames;
    }

    public Collection<String> findAllTags() {
        return session.createCriteria(Tag.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

    public Collection<String> findAllPropertyNames() {
        return session.createCriteria(Note.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

}
