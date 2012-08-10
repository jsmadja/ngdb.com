package com.ngdb.web.components.common.layout;

import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.pages.shop.ShopItemView;

public class Menu {

	@Property
	private List<ShopItem> shopItems;

	@Property
	private ShopItem shopItem;

	@Property
	private Long forSaleCount;

	@InjectPage
	private ShopItemView shopItemView;

	@Inject
	private com.ngdb.entities.Market market;

	@SetupRender
	void onInit() {
		this.shopItems = market.findRandomForSaleItems(3);
		this.forSaleCount = market.getNumForSaleItems();
	}

	public String getPrice() {
		return market.getPriceOf(shopItem);
	}

}
