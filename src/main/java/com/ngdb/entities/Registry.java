package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.apache.commons.lang.StringUtils.isNotBlank;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public List<Article> findGamesMatching(final String search) {
        List<Article> articles = new ArrayList<Article>();
        if (isNotBlank(search)) {
            final String searchItem = search.toLowerCase().trim();
            List<Article> allArticles = findAllArticles();
            Collection<Article> matchingArticles = filter(allArticles, new Predicates.Matching(searchItem));
            articles.addAll(matchingArticles);
            sort(articles, byTitlePlatformOrigin);
        }
        return articles;
    }

    public List<Article> findAllArticles() {
        List<Article> articles = new ArrayList<Article>();
        articles.addAll(gameFactory.findAll());
        articles.addAll(hardwareFactory.findAll());
        return articles;
    }

    public List<Game> findLastUpdates() {
        return session.createCriteria(Game.class).setCacheable(true).setMaxResults(10).addOrder(desc("modificationDate")).list();
    }

    public Collection<String> findAllTags() {
        return session.createCriteria(Tag.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

    public Collection<String> findAllPropertyNames() {
        return session.createCriteria(Note.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

}
