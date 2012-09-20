package com.ngdb.entities;

import com.ngdb.Predicates;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Tag;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;

public class Registry {

    public static final int MAX_RESULT_TO_RETURN = 100;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private Session session;

    private static final Logger LOG = LoggerFactory.getLogger(Registry.class);

    public List<Game> findGamesMatching(final String search) {
        List<Game> foundGames = new ArrayList<Game>();

        Set<Long> ids = new TreeSet<Long>();

        long t = System.currentTimeMillis();
        if (isNotBlank(search)) {
            final String searchItem = search.toLowerCase().trim();
            List<Game> allGames = articleFactory.findAllGames();
            Collection<Game> matchingGames = filter(allGames, new Predicates.Matching(searchItem));
            for (Article matchingArticle : matchingGames) {
                if(!ids.contains(matchingArticle.getId())) {
                    Game game = (Game) matchingArticle;
                    foundGames.add(game);
                    ids.add(game.getId());
                    String ngh = game.getNgh();
                    boolean shouldLinkWithOtherGames = ids.size() < MAX_RESULT_TO_RETURN && StringUtils.isNotBlank(ngh);
                    if(shouldLinkWithOtherGames) {
                        List<Game> linkedGames = articleFactory.findAllGamesByNgh(ngh);
                        for (Game linkedGame : linkedGames) {
                            if(!ids.contains(linkedGame.getId())) {
                                foundGames.add(linkedGame);
                                ids.add(linkedGame.getId());
                            }
                        }
                    }
                }
            }
            sort(foundGames, byTitlePlatformOrigin);
        }
        LOG.info("execution time: " + (System.currentTimeMillis() - t) + " ms");
        return foundGames;
    }

    public Collection<String> findAllTags() {
        return session.createCriteria(Tag.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

    public Collection<String> findAllPropertyNames() {
        return session.createCriteria(Note.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

}
