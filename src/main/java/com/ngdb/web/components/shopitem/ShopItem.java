package com.ngdb.web.components.shopitem;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class ShopItem {

	private static final int MAX_TITLE_LENGTH = 24;

	@Property
	@Parameter
	private com.ngdb.entities.shop.ShopItem shopItem;

	@Inject
	private CurrentUser currentUser;

	public String getShopItemMainPicture() {
		return shopItem.getArticle().getMainPicture().getUrl("medium");
	}

	public User getUser() {
		return currentUser.getUser();
	}

	public String getPrice() {
		if (currentUser.isFrench()) {
			return shopItem.getPriceInEuros() + " €";
		}
		return "$" + shopItem.getPriceInEuros();
	}

	public String getTitle() {
		if (shopItem.getTitle().length() > MAX_TITLE_LENGTH) {
			return shopItem.getTitle().substring(0, MAX_TITLE_LENGTH) + "...";
		}
		return shopItem.getTitle();
	}

}
