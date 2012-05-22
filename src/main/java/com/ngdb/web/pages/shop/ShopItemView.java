package com.ngdb.web.pages.shop;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.domain.UserService;

public class ShopItemView {

	@Persist("entity")
	private ShopItem shopItem;

	@Inject
	private UserService userService;

	public boolean isEditable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && userService.isLoggedUser(seller);
	}

	public boolean isBuyable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && !userService.isLoggedUser(seller);
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

}
