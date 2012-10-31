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

import java.util.Collection;

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

    public Collection<ShopItem> getBasket() {
        User user = currentUser.getUser();
        return user.getBasket().all();
    }

    public String getTotalPrice() {
        double total = 0;
        String preferredCurrency = currentUser.getPreferedCurrency();
        for (ShopItem item : getBasket()) {
            total+=item.getPriceIn(preferredCurrency);
        }
        return preferredCurrency +" "+total;
    }

    @CommitAfter
    Object onActionFromRemove(ShopItem shopItem) {
        market.removeFromBasket(currentUser.getUser(), shopItem);
        return Basket.class;
    }

    @CommitAfter
    Object onActionFromCheckout() {
        currentUser.checkout();
        market.refresh();
        return Basket.class;
    }

}
