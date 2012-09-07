package com.ngdb.web.pages;

import com.ngdb.entities.AccessoryFactory;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.MuseumFilter;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.hibernate.Session;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Museum {

    @Property
    private Article article;

    @Property
    private Platform platform;

    @Property
    private Origin origin;

    @Property
    private Publisher publisher;

    @Inject
    private ReferenceService referenceService;

    @Persist
    private MuseumFilter filter;

    @Inject
    private GameFactory gameFactory;

    @Inject
    private HardwareFactory hardwareFactory;

    @Inject
    private AccessoryFactory accessoryFactory;

    @Persist
    @Property
    private boolean thumbnailMode;

    @Inject
    private Request request;

    @Persist
    private User user;

    void onActivate() {
        if (filter == null || "true".equals(request.getParameter("display-all"))) {
            filter = new MuseumFilter(gameFactory, hardwareFactory, accessoryFactory);
        }
    }

    boolean onActivate(User user) {
        filter = new MuseumFilter(gameFactory, hardwareFactory, accessoryFactory);
        filter.filterByUser(user);
        this.user = user;
        return true;
    }

    boolean onActivate(String filterName, String value) {
        if (value == null) {
            onActivate();
            return true;
        }
        filter = new MuseumFilter(gameFactory, hardwareFactory, accessoryFactory);
        Filter filter = Filter.valueOf(Filter.class, filterName);
        switch (filter) {
        case byOrigin:
            this.filter.filterByOrigin(referenceService.findOriginByTitle(value));
            break;
        case byPlatform:
            this.filter.filterByPlatform(referenceService.findPlatformByName(value));
            break;
        case byPublisher:
            this.filter.filterByPublisher(referenceService.findPublisherBy(Long.valueOf(value)));
            break;
        case byTag:
            this.filter.filterByTag(referenceService.findTagById(Long.valueOf(value)));
            break;
        case byNgh:
            this.filter.filterByNgh(value);
            break;
        case byReleaseDate:
            this.filter.filterByReleaseDate(toReleaseDate(value));
            break;
        }
        return true;
    }

    private Date toReleaseDate(String value) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public List<Article> getArticles() {
        return filter.getArticles();
    }

    Object onActionFromClearFilters() {
        filter.clear();
        return this;
    }

    public int getNumResults() {
        return getArticles().size();
    }

    public long getNumHardwares() {
        if (user != null) {
            return ((BigInteger)session.createSQLQuery("SELECT COUNT(article_id) FROM CollectionObject WHERE user_id = "+user.getId()+" AND article_id IN (SELECT id FROM Hardware);").uniqueResult()).longValue();
        }
        return hardwareFactory.getNumHardwares();
    }

    Object onActionFromSelectHardwares() {
        filter.clear();
        filter.filterByHardwares();
        return this;
    }

    Object onActionFromSelectGames() {
        filter.clear();
        filter.filterByGames();
        return this;
    }

    Object onActionFromSelectAccessories() {
        filter.clear();
        filter.filterByAccessories();
        return this;
    }

    @Inject
    private Session session;

    public long getNumGames() {
        if (user != null) {
            return ((BigInteger)session.createSQLQuery("SELECT COUNT(article_id) FROM CollectionObject WHERE user_id = "+user.getId()+" AND article_id IN (SELECT id FROM Game);").uniqueResult()).longValue();
        }
        return gameFactory.getNumGames();
    }

    public long getNumAccessories() {
        if (user != null) {
            return ((BigInteger)session.createSQLQuery("SELECT COUNT(article_id) FROM CollectionObject WHERE user_id = "+user.getId()+" AND article_id IN (SELECT id FROM Accessory);").uniqueResult()).longValue();
        }
        return accessoryFactory.getNumAccessories();
    }

    public List<Platform> getPlatforms() {
        return referenceService.getPlatforms();
    }

    public boolean isArticleInThisPlatform() {
        return getNumArticlesInThisPlatform() > 0;
    }

    public boolean isFilteredByThisPlatform() {
        if (platform == null) {
            return false;
        }
        return filter.isFilteredBy(platform);
    }

    Object onActionFromFilterPlatform(Platform platform) {
        filter.filterByPlatform(platform);
        return this;
    }

    public int getNumArticlesInThisPlatform() {
        return filter.getNumArticlesInThisPlatform(platform);
    }

    public List<Origin> getOrigins() {
        return referenceService.getOrigins();
    }

    public boolean isFilteredByThisOrigin() {
        return filter.isFilteredBy(origin);
    }

    public boolean isArticleInThisOrigin() {
        if (origin == null) {
            return false;
        }
        return filter.getNumArticlesInThisOrigin(origin) > 0;
    }

    Object onActionFromThumbnailMode() {
        this.thumbnailMode = true;
        return this;
    }

    Object onActionFromGridMode() {
        this.thumbnailMode = false;
        return this;
    }

    Object onActionFromFilterOrigin(Origin origin) {
        filter.filterByOrigin(origin);
        return this;
    }

    public int getNumArticlesInThisOrigin() {
        return filter.getNumArticlesInThisOrigin(origin);
    }

    public boolean isArticleInThisPublisher() {
        return filter.getNumArticlesInThisPublisher(publisher) > 0;
    }

    public boolean isFilteredByThisPublisher() {
        if (publisher == null) {
            return false;
        }
        return filter.isFilteredBy(publisher);
    }

    Object onActionFromFilterPublisher(Publisher publisher) {
        filter.filterByPublisher(publisher);
        return this;
    }

    public List<Publisher> getPublishers() {
        return referenceService.getPublishers();
    }

    public User getUser() {
        return filter.getFilteredUser();
    }

    public boolean isFilteredByGames() {
        return filter.isFilteredByGames();
    }

    public int getNumArticlesInThisPublisher() {
        return filter.getNumArticlesInThisPublisher(publisher);
    }

    public Tag getTag() {
        return filter.getFilteredTag();
    }

    public Date getReleaseDate() {
        return filter.getFilteredReleaseDate();
    }

    public String getQueryLabel() {
        return filter.getQueryLabel();
    }

    public String getViewPage() {
        return article.getViewPage();
    }

    public String getGameSelected() {
        return filter.isFilteredByGames() ? "selected":"";
    }

    public String getHardwareSelected() {
        return filter.isFilteredByHardwares() ? "selected":"";
    }

    public String getAccessorySelected() {
        return filter.isFilteredByAccessories() ? "selected":"";
    }

}
