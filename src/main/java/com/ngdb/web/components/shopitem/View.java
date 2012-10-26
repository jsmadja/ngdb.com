package com.ngdb.web.components.shopitem;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

public class View {

    @Property
    @Parameter
    private com.ngdb.entities.shop.ShopItem shopItem;

    @Inject
    private CurrentUser currentUser;

    @Property
    private String message;

    @Inject
    private PictureService pictureService;

    @SetupRender
    void onRender() {
        if(shopItem != null && currentUser.isLogged()) {
            User potentialBuyer = currentUser.getUserFromDb();
            if (currentUser.isAnonymous()) {
                this.message = "You have to register to buy this item.";
            } else if (currentUser.isSeller(shopItem)) {
                this.message = "You are the seller of this item.";
            } else if (shopItem.isAlreadyWantedBy(potentialBuyer)) {
                this.message = "This item is already in your basket";
            }
        }
    }

    public String getShopItemMainPicture() {
        if(shopItem == null) {
            return "";
        }
        return pictureService.getCoverOf(shopItem).getUrl("medium");
    }

}
