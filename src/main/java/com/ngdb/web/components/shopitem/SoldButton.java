package com.ngdb.web.components.shopitem;

import com.ngdb.entities.Market;
import com.ngdb.entities.shop.ShopItem;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class SoldButton {

	@Property
	@Parameter
	private ShopItem shopItem;

	@InjectPage
	private com.ngdb.web.pages.Market marketPage;

	@Property
	@Parameter
	private boolean asButton;

    @Inject
    private Session session;

    @Inject
    private Market market;

	@CommitAfter
	Object onActionFromSold(ShopItem shopItem) {
        market.sell(shopItem);
        return marketPage;
	}

	@CommitAfter
	Object onActionFromSoldButton(ShopItem shopItem) {
		return onActionFromSold(shopItem);
	}
}
