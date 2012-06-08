package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.pages.shop.ShopItemView;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class BuyButton {

	@Property
	@Parameter
	private ShopItem shopItem;

	@Inject
	private CurrentUser currentUser;

	@InjectPage
	private ShopItemView shopItemView;

	Object onActionFromBuy(ShopItem shopItem) {
		currentUser.buy(shopItem);
		shopItemView.setShopItem(shopItem);
		return shopItemView;
	}

}
