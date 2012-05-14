package com.ngdb.web.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import com.ngdb.domain.ShopItem;
import com.ngdb.domain.ShopItems;

public class Shop {

	@Property
	private ShopItem shopItem;

	@Property
	private List<ShopItem> shopItems;

	@SetupRender
	public void setupRender() {
		shopItems = ShopItems.findAll();
	}

}
