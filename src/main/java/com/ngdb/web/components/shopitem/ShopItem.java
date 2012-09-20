package com.ngdb.web.components.shopitem;

import com.ngdb.entities.Market;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import static org.apache.tapestry5.EventConstants.ACTION;

public class ShopItem {

	private static final int MAX_TITLE_LENGTH = 24;

	@Property
	@Parameter
	private com.ngdb.entities.shop.ShopItem shopItem;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private Market market;

    @Component
    private Zone myZone;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    public JSONObject getParams() {
        return new JSONObject("width", "750", "modal", "false", "dialogClass", "dialog-edition", "zIndex", "1002", "title", "Buy this item");
    }

    public String getShopItemMainPicture() {
		return shopItem.getMainPicture().getUrl("medium");
	}

	public User getUser() {
		return currentUser.getUser();
	}

	public String getPrice() {
		return market.getPriceOf(shopItem);
	}

	public String getTitle() {
		if (shopItem.getTitle().length() > MAX_TITLE_LENGTH) {
			return shopItem.getTitle().substring(0, MAX_TITLE_LENGTH) + "...";
		}
		return shopItem.getTitle();
	}

    @OnEvent(component = "link1", value = ACTION)
    public void onActionFromLink1(com.ngdb.entities.shop.ShopItem shopItem) {
        this.shopItem = shopItem;
        ajaxResponseRenderer.addRender(myZone);
    }

    @OnEvent(component = "link2", value = ACTION)
    public void onActionFromLink2(com.ngdb.entities.shop.ShopItem shopItem) {
        this.shopItem = shopItem;
        ajaxResponseRenderer.addRender(myZone);
    }

    @OnEvent(component = "link3", value = ACTION)
    public void onActionFromLink3(com.ngdb.entities.shop.ShopItem shopItem) {
        this.shopItem = shopItem;
        ajaxResponseRenderer.addRender(myZone);
    }

}
