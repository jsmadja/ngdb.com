package com.ngdb.web.pages;

import com.google.common.collect.Collections2;
import com.ngdb.Predicates;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

public class Stats {

    @Inject
    private com.ngdb.entities.Market market;

    @Inject
    private Population population;

    @Inject
    private WishBox wishBox;

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    @Property
    private Long wishListCount;

    @Property
    private Long userCount;

    @Property
    private Long soldCount;

    @Property
    private Article mostOwnedArticle;
    @Property
    private Long mostOwnedArticleCount;

    @Property
    private Article mostWishedArticle;
    @Property
    private Long mostWishedArticleCount;

    @Property
    private Article mostForSaleArticle;
    @Property
    private Long mostForSaleArticleCount;

    @Property
    private User biggestCollectionner;
    @Property
    private Long biggestCollectionnerCount;

    @Property
    private User biggestWisher;
    @Property
    private Long biggestWisherCount;

    @Property
    private Tag mostUsedTag;
    @Property
    private Long mostUsedTagCount;

    @Inject
    private ReferenceService referenceService;

    @SetupRender
    public void init() {
        this.userCount = population.getNumUsers();
        this.wishListCount = wishBox.getNumWishes();
        this.soldCount = market.getNumSoldItems();

        EntityCount entityCount = entityCountQuery("SELECT article_id,COUNT(*) FROM CollectionObject GROUP BY article_id ORDER BY COUNT(*) DESC");
        this.mostOwnedArticle = articleFactory.findById(entityCount.entityId);
        this.mostOwnedArticleCount = entityCount.count;

        entityCount = entityCountQuery("SELECT article_id,COUNT(*) FROM Wish GROUP BY article_id ORDER BY COUNT(*) DESC");
        this.mostWishedArticle = articleFactory.findById(entityCount.entityId);
        this.mostWishedArticleCount = entityCount.count;

        entityCount = entityCountQuery("SELECT article_id,COUNT(*) FROM ShopItem GROUP BY article_id ORDER BY COUNT(*) DESC");
        this.mostForSaleArticle = articleFactory.findById(entityCount.entityId);
        this.mostForSaleArticleCount = entityCount.count;

        entityCount = entityCountQuery("SELECT user_id,COUNT(*) FROM CollectionObject GROUP BY user_id ORDER BY COUNT(*) DESC");
        this.biggestCollectionner = population.findById(entityCount.entityId);
        this.biggestCollectionnerCount = entityCount.count;

        entityCount = entityCountQuery("SELECT user_id,COUNT(*) FROM Wish GROUP BY user_id ORDER BY COUNT(*) DESC");
        this.biggestWisher = population.findById(entityCount.entityId);
        this.biggestWisherCount = entityCount.count;

        entityCount = entityCountQuery("SELECT id,COUNT(*) FROM Tag GROUP BY name ORDER BY COUNT(*) DESC");
        this.mostUsedTag = (Tag) session.load(Tag.class, entityCount.entityId);
        this.mostUsedTagCount = entityCount.count;
    }

    public String getCoverProgress() {
        StringBuilder sb = new StringBuilder();

        List<Platform> platforms = referenceService.getPlatforms();
        for (Platform platform : platforms) {
            Collection<Game> games = articleFactory.findAllGamesByPlatform(platform);
            Collection<Game> gamesWithPictures = Collections2.filter(games, Predicates.hasPicture);
            int numGamesWithPictures = gamesWithPictures.size();
            int numGames = games.size();
            int percent = (int) ((numGamesWithPictures / (float) numGames) * 100);
            sb.append("<tr>");
            sb.append("<td><b>").append(platform.getName()).append("</b></td>");
            sb.append("<td>").append(progressBar(percent)).append("</td>");
            sb.append("</tr>");
        }
        return sb.toString();
    }

    public String getReviewProgress() {
        StringBuilder sb = new StringBuilder();
        List<Game> noReviewGames = articleFactory.findAllGames();
        List<Game> games = articleFactory.findAllGames();
        for (Game game : games) {
            if(game.getHasReviews()) {
                noReviewGames.removeAll(articleFactory.findAllGamesByNgh(game.getNgh()));
            }
        }
        int numTotalGames = games.size();
        int numReviewedGames = numTotalGames - noReviewGames.size();
        int percent = (int) ((numReviewedGames / (float) numTotalGames) * 100);
        sb.append(progressBar(percent));
        return sb.toString();
    }

    public String getTagProgress() {
        StringBuilder sb = new StringBuilder();
        List<Game> noTagGames = articleFactory.findAllGames();
        List<Game> games = articleFactory.findAllGames();
        for (Game game : games) {
            if(game.hasTags()) {
                noTagGames.remove(game);
            }
        }
        int numTotalGames = games.size();
        int numTagGames = numTotalGames - noTagGames.size();
        int percent = (int) ((numTagGames / (float) numTotalGames) * 100);
        sb.append(progressBar(percent));
        return sb.toString();
    }

    private String progressBar(int percent) {
        return "<div class=\"progress\" style=\"width:100px\" ><div class=\"bar\" style=\"width: "+percent+"%;\"></div></div>";
    }

    public String getPictureContributors() {
        StringBuilder sb = new StringBuilder();
        List<Object> list = session.createQuery("SELECT DISTINCT(text) FROM Note n WHERE name = 'Picture courtesy of' ORDER BY text").list();
        for (Object object : list) {
            sb.append("<li>");
            sb.append(object);
            sb.append("</li>");
        }
        return sb.toString();
    }

    private EntityCount entityCountQuery(String query) {
        return new EntityCount(session.createSQLQuery(query).setMaxResults(1).list().get(0));
    }

    class EntityCount {
        Long entityId;
        Long count;

        EntityCount(Object o) {
            Object[] data = (Object[]) o;
            entityId = ((BigInteger) data[0]).longValue();
            count = ((BigInteger) data[1]).longValue();
        }
    }

}
