package com.ngdb.web.components.shopitem;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import com.ngdb.entities.shop.ShopItem;

public class SoldButton {

	@Property
	@Parameter
	private ShopItem shopItem;

	@InjectPage
	private com.ngdb.web.pages.Market marketPage;

	@Property
	@Parameter
	private boolean asButton;

	@CommitAfter
	Object onActionFromSold(ShopItem shopItem) {
		shopItem.sold();
		return marketPage;
	}

	@CommitAfter
	Object onActionFromSoldButton(ShopItem shopItem) {
		return onActionFromSold(shopItem);
	}
}
