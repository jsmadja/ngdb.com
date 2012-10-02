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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.hibernate.criterion.Projections.countDistinct;
import static org.hibernate.criterion.Restrictions.isNull;

public class Contribute {

    public static final String SELECT_GAME = "SELECT * FROM Game";
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
    private List<Article> coverUrl;
    @Persist
    private List<Article> upc;
    @Persist
    private List<? extends Article> review;
    @Persist
    private List<? extends Article> tag;

    @SetupRender
    void init() {
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
        List<Game> reviewedGames = session.createSQLQuery(SELECT_GAME+" WHERE ngh in (SELECT ngh FROM Game WHERE id in (SELECT distinct article_id FROM Review))").addEntity(Game.class).list();
        List<Game> noReviewGames = session.createSQLQuery(SELECT_GAME).addEntity(Game.class).list();
        noReviewGames.removeAll(reviewedGames);
        sort(noReviewGames, byTitlePlatformOrigin);
        return noReviewGames;
    }

    public List<? extends Article> getMissingTagArticles() {
        return tag;
    }

    private List<? extends Article> computeMissingTagArticles() {
        List<Game> noTagGames = session.createSQLQuery(SELECT_GAME +" WHERE id not in (SELECT distinct article_id FROM Tag)").addEntity(Game.class).list();
        sort(noTagGames, byTitlePlatformOrigin);
        return noTagGames;
    }

    private List<Article> getArticlesWithMissingProperty(String nullProperty) {
        List<Article> articles = new ArrayList<Article>();
        articles.addAll(session.createCriteria(Article.class).add(isNull(nullProperty)).list());
        sort(articles, byTitlePlatformOrigin);
        return articles;
    }

}
