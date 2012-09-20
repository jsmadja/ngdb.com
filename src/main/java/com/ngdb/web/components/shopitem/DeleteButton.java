package com.ngdb.web.components.shopitem;

import com.ngdb.entities.Market;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

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
		return marketPage;
	}

	@CommitAfter
	Object onActionFromRemoveButton(ShopItem shopItem) {
		return onActionFromRemove(shopItem);
	}

	@CommitAfter
	Object onActionFromRemove() {
		market.remove(shopItem);
		return marketPage;
	}
}
