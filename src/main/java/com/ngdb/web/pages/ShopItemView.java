package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.ShopItem;
import com.ngdb.entities.User;
import com.ngdb.web.services.UserService;

public class ShopItemView {

	@Property
	@Persist("entity")
	private ShopItem shopItem;

	@Inject
	private UserService userService;

	public void onActivate(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public boolean isEditable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && userService.isLoggedUser(seller);
	}

	public boolean isBuyable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && !userService.isLoggedUser(seller);
	}

}
