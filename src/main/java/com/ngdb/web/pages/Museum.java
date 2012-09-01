package com.ngdb.web.pages;

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
    private MuseumFilter museumFilter;

    @Inject
    private GameFactory gameFactory;

    @Inject
    private HardwareFactory hardwareFactory;

    @Persist
    @Property
    private boolean thumbnailMode;

    @Inject
    private Request request;

    void onActivate() {
        if (museumFilter == null || "true".equals(request.getParameter("display-all"))) {
            museumFilter = new MuseumFilter(gameFactory, hardwareFactory);
        }
    }

    boolean onActivate(User user) {
        museumFilter = new MuseumFilter(gameFactory, hardwareFactory);
        museumFilter.filterByUser(user);
        return true;
    }

    boolean onActivate(String filterName, String value) {
        if (value == null) {
            onActivate();
            return true;
        }
        museumFilter = new MuseumFilter(gameFactory, hardwareFactory);
        Filter filter = Filter.valueOf(Filter.class, filterName);
        switch (filter) {
        case byOrigin:
            museumFilter.filterByOrigin(referenceService.findOriginByTitle(value));
            break;
        case byPlatform:
            museumFilter.filterByPlatform(referenceService.findPlatformByName(value));
            break;
        case byPublisher:
            museumFilter.filterByPublisher(referenceService.findPublisherBy(Long.valueOf(value)));
            break;
        case byTag:
            museumFilter.filterByTag(referenceService.findTagById(Long.valueOf(value)));
            break;
        case byNgh:
            museumFilter.filterByNgh(value);
            break;
        case byReleaseDate:
            museumFilter.filterByReleaseDate(toReleaseDate(value));
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
        return museumFilter.getArticles();
    }

    Object onActionFromClearFilters() {
        museumFilter.clear();
        return this;
    }

    public int getNumResults() {
        return getArticles().size();
    }

    public long getNumHardwares() {
        return museumFilter.getNumHardwares();
    }

    Object onActionFromSelectHardwares() {
        museumFilter.filterByHardwares();
        return this;
    }

    Object onActionFromSelectGames() {
        museumFilter.filterByGames();
        return this;
    }

    public long getNumGames() {
        return museumFilter.getNumGames();
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
        return museumFilter.isFilteredBy(platform);
    }

    Object onActionFromFilterPlatform(Platform platform) {
        museumFilter.filterByPlatform(platform);
        return this;
    }

    public int getNumArticlesInThisPlatform() {
        return museumFilter.getNumArticlesInThisPlatform(platform);
    }

    public List<Origin> getOrigins() {
        return referenceService.getOrigins();
    }

    public boolean isFilteredByThisOrigin() {
        return museumFilter.isFilteredBy(origin);
    }

    public boolean isArticleInThisOrigin() {
        if (origin == null) {
            return false;
        }
        return museumFilter.getNumArticlesInThisOrigin(origin) > 0;
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
        museumFilter.filterByOrigin(origin);
        return this;
    }

    public int getNumArticlesInThisOrigin() {
        return museumFilter.getNumArticlesInThisOrigin(origin);
    }

    public boolean isArticleInThisPublisher() {
        return museumFilter.getNumArticlesInThisPublisher(publisher) > 0;
    }

    public boolean isFilteredByThisPublisher() {
        if (publisher == null) {
            return false;
        }
        return museumFilter.isFilteredBy(publisher);
    }

    Object onActionFromFilterPublisher(Publisher publisher) {
        museumFilter.filterByPublisher(publisher);
        return this;
    }

    public List<Publisher> getPublishers() {
        return referenceService.getPublishers();
    }

    public User getUser() {
        return museumFilter.getFilteredUser();
    }

    public boolean isFilteredByGames() {
        return museumFilter.isFilteredByGames();
    }

    public int getNumArticlesInThisPublisher() {
        return museumFilter.getNumArticlesInThisPublisher(publisher);
    }

    public Tag getTag() {
        return museumFilter.getFilteredTag();
    }

    public Date getReleaseDate() {
        return museumFilter.getFilteredReleaseDate();
    }

    public String getQueryLabel() {
        return museumFilter.getQueryLabel();
    }

    public String getViewPage() {
        return article.getViewPage();
    }

}
