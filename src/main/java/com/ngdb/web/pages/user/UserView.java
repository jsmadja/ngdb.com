package com.ngdb.web.pages.user;

import com.ngdb.entities.Market;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Basket;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
import com.ngdb.web.pages.Index;
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
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.hibernate.Session;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.apache.tapestry5.EventConstants.ACTION;

public class UserView {

    @Persist
    private User user;

    @Property
    private Wish wish;

    @Property
    private Article article;

    @Property
    private ShopItem shopItem;

    @Property
    private ShopItem currentOrder;

    @Property
    private User potentialBuyer;

    @Property
    private User seller;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Session session;

    @Inject
    private Market market;

    @Component
    private Zone codeForumZone;

    @Component
    private Zone shopItemZone;

    @Inject
    private Request request;

    @Property
    private JSONObject params;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

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
        return codeForumZone;
    }

    void onActivate(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public boolean isBuyable() {
        return currentUser.canBuy(getShopItemFromDb());
    }

    public Collection<ShopItem> shopItemsForSale() {
        return market.getShopItemsForSaleOf(user);
    }

    private ShopItem getShopItemFromDb() {
        return (ShopItem) session.load(ShopItem.class, shopItem.getId());
    }

    public Set<User> getSellers() {
        return market.findSellersOf(wish.getArticle());
    }

    public Set<User> getPotentialBuyers() {
        return shopItem.getPotentialBuyers();
    }

    public String getPrice() {
        return market.getPriceOf(shopItem);
    }

    public Basket getBasket() {
        return user.getBasket();
    }

    public String getOrderPrice() {
        return market.getPriceOf(currentOrder);
    }

    public String getForumCode() {
        return market.asVBulletinCode();
    }

    public Collection<ShopItem> getShopItemsForSale() {
        List<ShopItem> shopItems = market.getShopItemsForSaleOf(user);
        Collections.sort(shopItems);
        return shopItems;
    }

    @OnEvent(component = "shopItemLink", value = ACTION)
    public Object onActionFromShopItemKink(ShopItem shopItem) {
        this.shopItem = shopItem;
        if (!request.isXHR()) {
            return Index.class;
        }
        return shopItemZone;
    }

    public boolean isTheCurrentUser() {
        return currentUser.equalsThis(user);
    }

}
