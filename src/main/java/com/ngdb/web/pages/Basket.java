package com.ngdb.web.pages;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.Set;

@RequiresUser
public class Basket {

    @Inject
    private CurrentUser currentUser;

    @Inject
    private com.ngdb.entities.Market market;

    @Property
    private ShopItem shopItem;

    @InjectComponent
    private Zone basketZone;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @SetupRender
    public void init() {
    }

    @CommitAfter
    public void onSuccess() {
        currentUser.checkout();
    }

    public Set<ShopItem> getShopItems() {
        User user = currentUser.getUserFromDb();
        return user.getPotentialBuys();
    }

    public String getTotalPrice() {
        double totalInDollars = 0;
        double totalInEuros = 0;

        Set<ShopItem> shopItems = getShopItems();
        for (ShopItem item : shopItems) {
            totalInDollars+=item.getPriceInDollars();
            totalInEuros+=item.getPriceInEuros();
        }
        return "$"+totalInDollars;
    }

    @CommitAfter
    Object onActionFromRemove(ShopItem shopItem) {
        market.removeFromBasket(currentUser.getUserFromDb(), shopItem);
        return Basket.class;
    }

    @CommitAfter
    Object onActionFromCheckout() {
        currentUser.checkout();
        return Basket.class;
    }

}
