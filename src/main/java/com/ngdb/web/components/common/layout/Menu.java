package com.ngdb.web.components.common.layout;

import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.tapestry5.EventConstants.ACTION;

public class Menu {

	@Property
	private List<ShopItem> shopItems;

	@Property
	private ShopItem shopItem;

	@Inject
	private com.ngdb.entities.Market market;

    @Inject
    private ActionLogger actionLogger;

    @Property
    private ArticleAction update;

    @Inject
    private Messages messages;

    private static boolean goEmpty = false;

    @Component
    private Zone myZone;

    @Inject
    private Request request;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Inject
    private PictureService pictureService;

    public JSONObject getParams() {
        return new JSONObject("width", "750", "modal", "false", "dialogClass", "dialog-edition", "zIndex", "1002");
    }

    @SetupRender
	void onInit() {
        if(goEmpty) {
            this.shopItems = new ArrayList<ShopItem>();
        } else {
            this.shopItems = market.findRandomForSaleItems(6);
        }
	}

    @OnEvent(component = "link1", value = ACTION)
    public Object onActionFromLink1(ShopItem shopItem) {
        this.shopItem = shopItem;
        if (!request.isXHR()) {
            return Index.class;
        }
        return myZone;
    }

    @OnEvent(component = "link2", value = ACTION)
    public Object onActionFromLink2(ShopItem shopItem) {
        return onActionFromLink1(shopItem);
    }

    @OnEvent(component = "link3", value = ACTION)
    public Object onActionFromLink3(ShopItem shopItem) {
        return onActionFromLink1(shopItem);
    }

    public Collection<ArticleAction> getUpdates() {
        if(goEmpty) {
            return new ArrayList<ArticleAction>();
        } else {
            return actionLogger.listLastActions();
        }
    }

	public String getPrice() {
		return market.getPriceForCurrentUser(shopItem);
	}

    public String getUpdateMessage() {
        return messages.get("whatsnew."+update.getMessage());
    }

    public String getLastUpdateDate() {
        return update.getLastUpdateDate(request.getLocale());
    }

    public String getShopItemCover() {
        Picture cover = pictureService.getCoverOf(shopItem);
        return cover.getUrlSmall();
    }

}
