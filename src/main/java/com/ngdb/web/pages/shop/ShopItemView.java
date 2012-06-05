package com.ngdb.web.pages.shop;

import static com.ngdb.web.Category.byUser;

import org.apache.tapestry5.annotations.InjectPage;
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

	@InjectPage
	private com.ngdb.web.pages.Market marketPage;

	@SetupRender
	void onInit() {
		User potentialBuyer = currentUser.getUser();
		if (currentUser.isAnonymous()) {
			this.message = "You have to register to buy this item.";
		} else if (isSeller()) {
			this.message = "You are the seller of this item.";
		} else if (!shopItem.isNotAlreadyWantedBy(potentialBuyer)) {
			this.message = shopItem.getSeller().getLogin() + " has been contacted, he/she will send you an email as soon as possible.";
		}
	}

	private boolean isSeller() {
		return currentUser.isLoggedUser(shopItem.getSeller());
	}

	@CommitAfter
	Object onActionFromBuy() {
		market.potentialBuyer(shopItem, currentUser.getUser());
		return this;
	}

	@CommitAfter
	Object onActionFromRemove() {
		market.remove(shopItem);
		marketPage.setCategory(byUser);
		return marketPage;
	}

	@CommitAfter
	Object onActionFromSold() {
		shopItem.sold();
		marketPage.setCategory(byUser);
		return marketPage;
	}

	public boolean isEditable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && currentUser.isLoggedUser(seller);
	}

	public boolean isBuyable() {
		boolean currentUserIsPotentialBuyer = currentUser.isLogged() && shopItem.isNotAlreadyWantedBy(currentUser.getUser());
		boolean currentUserIsNotTheSeller = !isSeller();
		boolean isNotSold = !shopItem.isSold();
		return isNotSold && currentUserIsNotTheSeller && currentUserIsPotentialBuyer;
	}

	public boolean isSoldable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && currentUser.isLoggedUser(seller);
	}

	public boolean isRemoveable() {
		User seller = shopItem.getSeller();
		return !shopItem.isSold() && currentUser.isLoggedUser(seller);
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	public ShopItem getShopItem() {
		return shopItem;
	}

}
