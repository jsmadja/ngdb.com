package com.ngdb.web.components.shopitem;

import com.ngdb.StarsUtil;
import com.ngdb.entities.Market;
import com.ngdb.web.pages.Index;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.PropertyConduitSource;
import org.apache.tapestry5.services.Request;

import java.util.Collection;

import static org.apache.tapestry5.EventConstants.ACTION;

public class ShopItems {

    @Property
    @Parameter(allowNull=false)
    private Collection<com.ngdb.entities.shop.ShopItem> items;

    @Property
    private com.ngdb.entities.shop.ShopItem shopItem;

    @Persist
    @Property
    private boolean thumbnailMode;

    @Persist
    @Property
    private boolean tableMode;

    @Component
    private Zone shopItemZone;

    @Inject
    private Request request;

    @Inject
    private Market market;

    @Persist
    @Property
    private BeanModel<com.ngdb.entities.shop.ShopItem> model;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private PropertyConduitSource propertyConduitSource;

    @SetupRender
    void init() {
        if(!thumbnailMode && !tableMode) {
            onActionFromTableMode();
        }
        model = beanModelSource.createDisplayModel(com.ngdb.entities.shop.ShopItem.class, messages);
        model.get("title").sortable(true);
        model.add("price", null);
        model.add("actions", null);
        model.add("thumbnailColumn", null);
        model.get("price").sortable(true);
        model.include("thumbnailColumn", "title", "details", "price", "actions");
    }

    public JSONObject getParams() {
        return new JSONObject("width", "750", "modal", "false", "dialogClass", "dialog-edition", "zIndex", "1002");
    }

    public String getPrice() {
        return market.getPriceForCurrentUser(shopItem);
    }

    void onActionFromThumbnailMode() {
        this.thumbnailMode = true;
        this.tableMode = false;
    }

    void onActionFromTableMode() {
        this.thumbnailMode = false;
        this.tableMode = true;
    }

    public String getShopItemMainPictureSmall() {
        return shopItem.getMainPicture().getUrlSmall();
    }

    @OnEvent(component = "link1", value = ACTION)
    public Object onActionFromLink1(com.ngdb.entities.shop.ShopItem shopItem) {
        return onActionFromLink2(shopItem);
    }

    @OnEvent(component = "link2", value = ACTION)
    public Object onActionFromLink2(com.ngdb.entities.shop.ShopItem shopItem) {
        this.shopItem = shopItem;
        if (!request.isXHR()) {
            return Index.class;
        }
        return shopItemZone;
    }

    public String getStars() {
        return StarsUtil.getStars(shopItem);
    }

}
