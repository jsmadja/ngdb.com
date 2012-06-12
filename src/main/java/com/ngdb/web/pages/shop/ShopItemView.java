package com.ngdb.web.pages.shop;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class ShopItemView {

	@Persist("entity")
	private ShopItem shopItem;

	@Inject
	private CurrentUser currentUser;

	@Property
	private String message;

	@SetupRender
	void onInit() {
		User potentialBuyer = currentUser.getUser();
		if (currentUser.isAnonymous()) {
			this.message = "You have to register to buy this item.";
		} else if (currentUser.isSeller(shopItem)) {
			this.message = "You are the seller of this item.";
		} else if (!shopItem.isNotAlreadyWantedBy(potentialBuyer)) {
			this.message = shopItem.getSeller().getLogin() + " has been contacted, he/she will send you an email as soon as possible.";
		}
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

}
