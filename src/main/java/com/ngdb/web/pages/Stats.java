package com.ngdb.web.pages;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.services.SNK;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

import static org.hibernate.criterion.Projections.countDistinct;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.isNotNull;

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

    @Inject
    private SNK snk;

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

    @Inject
    private ReferenceService referenceService;

    @Property
    private Long employeeCount;

    @SetupRender
    public void init() {
        this.userCount = population.getNumUsers();
        this.wishListCount = wishBox.getNumWishes();
        this.soldCount = market.getNumSoldItems();
        this.employeeCount = snk.getNumEmployees();

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

    }

    public String getCoverProgress() {
        StringBuilder sb = new StringBuilder();

        List<Platform> platforms = referenceService.getPlatforms();
        for (Platform platform : platforms) {

            Criteria countCriteria = session.createCriteria(Game.class).setProjection(countDistinct("id")).add(eq("platformShortName", platform.getShortName()));
            long numGames = (Long) countCriteria.uniqueResult();
            long numGamesWithPictures = (Long) countCriteria.add(isNotNull("coverUrl")).uniqueResult();

            int percent = (int) ((numGamesWithPictures / (float) numGames) * 100);
            sb.append("<tr>");
            sb.append("<td><b>").append(platform.getName()).append("</b></td>");
            sb.append("<td>").append(progressBar(percent)).append("</td>");
            sb.append("</tr>");
        }
        return sb.toString();
    }

    public String getReviewProgress() {
        long numTotalGames = (Long) session.createCriteria(Game.class).setProjection(countDistinct("id")).uniqueResult();
        long numReviewedGames = ((BigInteger)session.createSQLQuery("SELECT count(id) FROM Game WHERE ngh in (SELECT ngh FROM Game WHERE id in (SELECT distinct article_id FROM Review))").uniqueResult()).longValue();
        int percent = (int) ((numReviewedGames / (float) numTotalGames) * 100);
        return progressBar(percent);
    }

    public String getTagProgress() {
        long numTotalGames = (Long) session.createCriteria(Game.class).setProjection(countDistinct("id")).uniqueResult();
        long numTagGames = (Long)session.createCriteria(Tag.class).setProjection(countDistinct("article")).uniqueResult();
        int percent = (int) ((numTagGames / (float) numTotalGames) * 100);
        return progressBar(percent);
    }

    public String getStaffProgress() {
        long numTotalGames = (Long) session.createCriteria(Game.class).setProjection(countDistinct("id")).uniqueResult();
        long numStaffGames = ((BigInteger)session.createSQLQuery("SELECT count(id) FROM Game WHERE ngh in (SELECT ngh FROM Game WHERE id in (SELECT distinct article_id FROM Participation))").uniqueResult()).longValue();
        int percent = (int) ((numStaffGames / (float) numTotalGames) * 100);
        return progressBar(percent);
    }

    public String getVideoProgress() {
        long numTotalGames = (Long) session.createCriteria(Game.class).setProjection(countDistinct("id")).uniqueResult();
        long numVideoGames = ((BigInteger)session.createSQLQuery("SELECT count(id) FROM Game WHERE ngh in (SELECT ngh FROM Game WHERE id in (SELECT distinct id FROM Game WHERE youtube_playlist IS NOT NULL))").uniqueResult()).longValue();
        int percent = (int) ((numVideoGames / (float) numTotalGames) * 100);
        return progressBar(percent);
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
