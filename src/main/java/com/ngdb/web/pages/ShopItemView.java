package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameShopItem;
import com.ngdb.entities.User;
import com.ngdb.web.services.UserService;

public class ShopItemView {

	@Property
	@Persist("entity")
	private GameShopItem gameShopItem;

	@Inject
	private UserService userService;

	public void onActivate(GameShopItem gameShopItem) {
		this.gameShopItem = gameShopItem;
	}

	public boolean isEditable() {
		User author = gameShopItem.getUser();
		return !gameShopItem.isSold() && userService.isLoggedUser(author);
	}

	public boolean isBuyable() {
		User author = gameShopItem.getUser();
		return !gameShopItem.isSold() && !userService.isLoggedUser(author);
	}

}
