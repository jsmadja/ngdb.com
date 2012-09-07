package com.ngdb.web.pages;

import com.ngdb.ForumCode;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.MarketFilter;
import com.ngdb.entities.Population;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;

import java.util.Collection;
import java.util.List;

public class Market {

    @Inject
    private com.ngdb.entities.Market market;

    @Property
    private String username;

    @Property
    private ShopItem shopItem;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private Population population;

    @Inject
    private ArticleFactory articleFactory;

    @Persist
    private MarketFilter filter;

    @Property
    private Platform platform;

    @Property
    private Origin origin;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Request request;

    @Component
    private Zone myZone;

    @Property
    private JSONObject params;

    @OnEvent(EventConstants.ACTIVATE)
    void init() {
        params = new JSONObject();
        params.put("width", 800);
        params.put("title", "Export to forums");
    }

    @OnEvent(EventConstants.ACTION)
    Object updateCount() {
        if (!request.isXHR()) {
            return this;
        }
        return myZone;
    }

    void onActivate() {
        if (filter == null || "true".equals(request.getParameter("display-all"))) {
            filter = new MarketFilter(market);
        }
    }

    boolean onActivate(User user) {
        filter = new MarketFilter(market);
        filter.filterByUser(user);
        return true;
    }

    boolean onActivate(String filterName, String value) {
        if (value == null) {
            onActivate();
            return true;
        }
        filter = new MarketFilter(market);
        Filter filter = Filter.valueOf(Filter.class, filterName);
        switch (filter) {
        case byUser:
            User user = population.findById(Long.valueOf(value));
            this.filter.filterByUser(user);
            break;
        case byArticle:
            Article article = articleFactory.findById(Long.valueOf(value));
            this.filter.filterByArticle(article);
            break;
        }
        return true;
    }

    public Collection<ShopItem> getShopItems() {
        return filter.getShopItems();
    }

    Object onActionFromClearFilters() {
        filter.clear();
        return this;
    }

    Object onActionFromSelectGames() {
        filter.clear();
        filter.filterByGames();
        return this;
    }

    Object onActionFromSelectHardwares() {
        filter.clear();
        filter.filterByHardwares();
        return this;
    }

    Object onActionFromSelectAccessories() {
        filter.clear();
        filter.filterByAccessories();
        return this;
    }

    public long getNumGames() {
        return filter.getNumGames();
    }

    public long getNumHardwares() {
        return filter.getNumHardwares();
    }

    public long getNumAccessories() {
        return filter.getNumAccessories();
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
        return filter.getNumShopItemsInThisPlatform(platform);
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
        return getNumArticlesInThisOrigin() > 0;
    }

    Object onActionFromFilterOrigin(Origin origin) {
        filter.filterByOrigin(origin);
        return this;
    }

    public int getNumArticlesInThisOrigin() {
        return filter.getNumShopItemsInThisOrigin(origin);
    }

    public User getUser() {
        return filter.getFilteredUser();
    }

    public boolean isFilteredByGames() {
        return filter.isFilteredByGames();
    }

    public boolean isFilteredByHardwares() {
        return filter.isFilteredByHardwares();
    }

    public boolean isFilteredByAccessories() {
        return filter.isFilteredByAccessories();
    }

    public String getQueryLabel() {
        return filter.getQueryLabel();
    }

    public String getViewPage() {
        return "/shopitem/ShopItemView";
    }

    public int getNumResults() {
        return getShopItems().size();
    }

    public boolean isLogged() {
        return currentUser.isLogged();
    }

    public String getForumCode() {
        List<ShopItem> shopItems = filter.getShopItems();
        return ForumCode.asVBulletinCode(shopItems);
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
