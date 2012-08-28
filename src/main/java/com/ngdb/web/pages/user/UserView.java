package com.ngdb.web.pages.user;

import com.ngdb.entities.Market;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
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
import org.hibernate.Session;

import java.util.Set;

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
    private Zone myZone;

    @Inject
    private Request request;

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

    void onActivate(User user) {
        this.user = user;
    }

    public String getViewPage() {
        if (wish == null) {
            return "";
        }
        Article article = wish.getArticle();
        Class<?> type = article.getType();
        if (type.equals(Game.class)) {
            return "article/game/gameView";
        }
        return "article/hardware/hardwareView";
    }

    public User getUser() {
        return user;
    }

    public boolean isBuyable() {
        return currentUser.canBuy(getShopItemFromDb());
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

    public Set<ShopItem> getCurrentOrders() {
        return user.getPotentialBuys();
    }

    public String getOrderPrice() {
        return market.getPriceOf(currentOrder);
    }

    public String getForumCode() {
        return market.asVBulletinCode();
    }

}
