package com.ngdb.web.pages;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.google.common.collect.Collections2;
import com.ngdb.Predicates;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;

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

    @Property
    private Integer missingCovers;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private GameFactory gameFactory;

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

        int cover = 0;
        List<Article> articles = articleFactory.findAll();
        for (Article article : articles) {
            if (article.hasCover()) {
                cover++;
            }
        }
        this.missingCovers = articles.size() - cover;
    }

    public String getCoverProgress() {
        StringBuilder sb = new StringBuilder();

        List<Platform> platforms = referenceService.getPlatforms();
        for (Platform platform : platforms) {
            Collection<Game> games = gameFactory.findAllByPlatform(platform);
            Collection<Game> gamesWithPictures = Collections2.filter(games, Predicates.hasPicture);
            int numGamesWithPictures = gamesWithPictures.size();
            int numGames = games.size();
            sb.append("<li>");
            sb.append("<b>").append(platform.getName()).append("</b> : ").append(numGamesWithPictures).append("/").append(numGames);
            sb.append(" - ");
            sb.append((int) ((float) (numGamesWithPictures / (float) numGames) * 100)).append("%");
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
