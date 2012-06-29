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
import com.ngdb.web.services.infrastructure.CurrentUser;

public class DeleteButton {

	@Inject
	private Session session;

	@Inject
	private CurrentUser currentUser;

	@InjectPage
	private com.ngdb.web.pages.Market marketPage;

	@Inject
	private Market market;

	@Property
	@Parameter
	private ShopItem shopItem;

	@Property
	@Parameter
	private boolean asButton;

	@CommitAfter
	Object onActionFromRemove(ShopItem shopItem) {
		session.delete(shopItem);
		marketPage.setUser(currentUser.getUser());
		return marketPage;
	}

	@CommitAfter
	Object onActionFromRemoveButton(ShopItem shopItem) {
		return onActionFromRemove(shopItem);
	}

	@CommitAfter
	Object onActionFromRemove() {
		market.remove(shopItem);
		marketPage.setCategory(byUser);
		return marketPage;
	}
}
