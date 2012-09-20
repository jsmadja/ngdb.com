package com.ngdb.web.components.shopitem;

import com.ngdb.entities.shop.ShopItem;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class EditButton {

	@Property
	@Parameter
	private ShopItem shopItem;

	@Property
	@Parameter
	private boolean asButton;

}
