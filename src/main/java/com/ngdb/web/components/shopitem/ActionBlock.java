package com.ngdb.web.components.shopitem;

import static com.ngdb.web.Category.byUser;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Market;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class ActionBlock {

	@Inject
	private CurrentUser currentUser;

	@Property
	@Parameter(required = false)
	private boolean showText;

	@Property
	@Parameter
	private ShopItem shopItem;

	@Inject
	private Market market;

	@InjectPage
	private com.ngdb.web.pages.Market marketPage;

	@Inject
	private Session session;

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

	@CommitAfter
	Object onActionFromSold(ShopItem shopItem) {
		shopItem.sold();
		marketPage.setUser(currentUser.getUser());
		return marketPage;
	}

	@CommitAfter
	Object onActionFromRemove(ShopItem shopItem) {
		session.delete(shopItem);
		marketPage.setUser(currentUser.getUser());
		return marketPage;
	}
}
