package com.ngdb.web.pages;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.hibernate.criterion.Restrictions.isNull;

public class Contribute {

    @Inject
    private Session session;

    @Property
    private Article article;

    @Persist
    @Property
    private BeanModel<Article> articleModel;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private ArticleFactory articleFactory;

    @Persist
    private List<Game> allGames;
    @Persist
    private List<Article> coverUrl;
    @Persist
    private List<Article> upc;
    @Persist
    private List<? extends Article> review;
    @Persist
    private List<? extends Article> tag;

    @SetupRender
    void init() {
        allGames = articleFactory.findAllGames();
        articleModel = beanModelSource.createDisplayModel(Article.class, messages);
        articleModel.get("platformShortName").label("Platform").sortable(true);
        articleModel.get("originTitle").label("Origin").sortable(true);
        coverUrl = getArticlesWithMissingProperty("coverUrl");
        upc = getArticlesWithMissingProperty("upc");
        review = computeMissingReviewArticles();
        tag = computeMissingTagArticles();
    }

    public List<Article> getMissingCoverArticles() {
        return coverUrl;
    }

    public List<Article> getMissingUPCArticles() {
        return upc;
    }

    public List<? extends Article> getMissingReviewArticles() {
        return review;
    }

    private List<? extends Article> computeMissingReviewArticles() {
        List<? extends Article> noReviewGames = new ArrayList<Article>(allGames);
        List<Game> games = new ArrayList<Game>(allGames);
        for (Game game : games) {
            if(game.getHasReviews()) {
                noReviewGames.removeAll(articleFactory.findAllGamesByNgh(game.getNgh()));
            }
        }
        sort(noReviewGames, byTitlePlatformOrigin);
        return noReviewGames;
    }

    public List<? extends Article> getMissingTagArticles() {
        return tag;
    }

    private List<? extends Article> computeMissingTagArticles() {
        List<? extends Article> noTagGames = new ArrayList<Game>(allGames);
        List<Game> games = new ArrayList<Game>(allGames);
        for (Game game : games) {
            if(!game.hasTags()) {
                noTagGames.remove(game);
            }
        }
        sort(noTagGames, byTitlePlatformOrigin);
        return noTagGames;
    }

    private List<Article> getArticlesWithMissingProperty(String nullProperty) {
        List<Article> articles = new ArrayList<Article>();
        /*
        articles.addAll(session.createCriteria(Game.class).add(isNull(nullProperty)).list());
        articles.addAll(session.createCriteria(Hardware.class).add(isNull(nullProperty)).list());
        articles.addAll(session.createCriteria(Accessory.class).add(isNull(nullProperty)).list());
        */
        articles.addAll(session.createCriteria(Article.class).add(isNull(nullProperty)).list());
        sort(articles, byTitlePlatformOrigin);
        return articles;
    }

}
