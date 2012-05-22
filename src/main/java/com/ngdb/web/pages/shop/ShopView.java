package com.ngdb.web.pages.shop;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.Category;
import com.ngdb.web.services.domain.ShopService;

public class ShopView {

	@Property
	private ShopItem shopItem;

	@Property
	private Collection<ShopItem> shopItems;

	@Inject
	private ShopService shopService;

	@Persist
	private Category category;

	@InjectPage
	private ShopItemView shopItemView;

	private Long id;

	void onActivate(String category, String value) {
		if (isNotBlank(category)) {
			this.category = Category.valueOf(Category.class, category);
			if (StringUtils.isNumeric(value)) {
				id = Long.valueOf(value);
			}
		}
	}

	@SetupRender
	public void init() {
		if (category == null) {
			category = Category.none;
		}
		this.shopItems = shopService.findAll(category, id);
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	Object onActionFromShopItem(ShopItem shopItem) {
		shopItemView.setShopItem(shopItem);
		return shopItemView;
	}
}
