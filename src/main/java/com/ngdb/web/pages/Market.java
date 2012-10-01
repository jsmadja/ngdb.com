package com.ngdb.web.pages;

import com.ngdb.ForumCode;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.MarketFilter;
import com.ngdb.entities.Population;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
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
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ContextPathEncoder;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

import static org.apache.tapestry5.EventConstants.ACTION;

public class Market {

    @Inject
    private com.ngdb.entities.Market market;

    @Property
    private String username;

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
    private Zone forumCodeZone;

    @Property
    private JSONObject params;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Inject
    private Messages messages;

    @Inject
    private Session session;

    @OnEvent(EventConstants.ACTIVATE)
    void init() {

        params = new JSONObject();
        params.put("width", 800);
        params.put("title", "Export to forums");
    }

    @OnEvent(component = "forumCodeLink", value = ACTION)
    void onActionFromForumCodeLink() {
        ajaxResponseRenderer.addRender(forumCodeZone);
    }

    void onActivate() {
        if (filter == null || "true".equals(request.getParameter("display-all"))) {
            filter = new MarketFilter(market, session);
        }
        filter.initializeWithUrlParameters(request, referenceService, population);
    }

    boolean onActivate(User user) {
        filter = new MarketFilter(market, session);
        filter.filterByUser(user);
        return true;
    }

    boolean onActivate(String filterName, String value) {
        if (value == null) {
            onActivate();
            return true;
        }
        filter = new MarketFilter(market, session);
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

    public int getNumResults() {
        return getShopItems().size();
    }

    public boolean isLogged() {
        return currentUser.isLogged();
    }

    public String getForumCode() {
        Collection<ShopItem> shopItems = filter.getShopItems();
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

    public String getFilterUrl() {
        return filter.getFilterUrl();
    }

    public String getTitle() {
        return messages.format("market.usermarket",username);
    }

}
