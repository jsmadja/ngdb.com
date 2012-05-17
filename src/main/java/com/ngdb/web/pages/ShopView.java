package com.ngdb.web.pages;

import java.util.Set;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import com.ngdb.entities.ShopItem;
import com.ngdb.entities.User;

public class ShopView {

	@Property
	private ShopItem shopItem;

	@Property
	private Set<ShopItem> shopItems;

	@Parameter(allowNull = true)
	private User user;

	@SetupRender
	public void setupRender() {
	}

}
