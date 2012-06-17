package com.ngdb.web.components.shopitem;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class ShopActionBlock {

	@Inject
	private CurrentUser currentUser;

	@Property
	@Parameter
	private ShopItem shopItem;

	public User getUser() {
		return currentUser.getUser();
	}

	public boolean isBuyable() {
		return currentUser.canBuy(shopItem);
	}

	public boolean isSoldable() {
		return currentUser.canMarkAsSold(shopItem);
	}

	public boolean isRemoveable() {
		return currentUser.canRemove(shopItem);
	}

	public boolean isEditable() {
		return currentUser.canEdit(shopItem);
	}

}
