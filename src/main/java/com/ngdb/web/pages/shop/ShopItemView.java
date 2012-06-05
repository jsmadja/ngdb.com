package com.ngdb.web.pages.shop;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Market;
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

	@Inject
	private Market market;

	@SetupRender
	void onInit() {
		User potentialBuyer = currentUser.getUser();
		if (currentUser.isLogged() && shopItem.isNotAlreadyWantedBy(potentialBuyer)) {
			this.message = shopItem.getSeller().getLogin() + " has been contacted, he/she will send you an email as soon as possible.";
		}
	}

	@CommitAfter
	Object onActionFromBuy() {
		market.potentialBuyer(shopItem, currentUser.getUser());
		return this;
	}

	public boolean isEditable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && currentUser.isLoggedUser(seller);
	}

	public boolean isBuyable() {
		User seller = shopItem.getSeller();
		User potentialBuyer = currentUser.getUser();
		return !shopItem.isSold() && currentUser.isLogged() && !currentUser.isLoggedUser(seller) && shopItem.isNotAlreadyWantedBy(potentialBuyer);
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

}
