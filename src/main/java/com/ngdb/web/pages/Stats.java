package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;

public class Stats {

	@Property
	private Long wishListCount;

	@Property
	private Long userCount;

	@Property
	private Long soldCount;

	@Inject
	private com.ngdb.entities.Market market;

	@Inject
	private Population population;

	@Inject
	private WishBox wishBox;

	@SetupRender
	public void init() {
		this.userCount = population.getNumUsers();
		this.wishListCount = wishBox.getNumWishes();
		this.soldCount = market.getNumSoldItems();
	}

}
