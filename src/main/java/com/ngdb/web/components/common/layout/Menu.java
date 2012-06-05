package com.ngdb.web.components.common.layout;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import com.ngdb.web.Category;
import com.ngdb.web.pages.Market;

public class Menu {

	@Property
	private Long gameCount;

	@Property
	private Long hardwareCount;

	@Property
	private Long wishListCount;

	@Property
	private Long userCount;

	@Property
	private Long soldCount;

	@Property
	private Long forSaleCount;

	@Inject
	private com.ngdb.entities.Market market;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private Population population;

	@Inject
	private WishBox wishBox;

	@InjectPage
	private Market marketPage;

	@SetupRender
	public void init() {
		this.gameCount = gameFactory.getNumGames();
		this.userCount = population.getNumUsers();
		this.hardwareCount = hardwareFactory.getNumHardwares();
		this.wishListCount = wishBox.getNumWishes();
		this.soldCount = market.getNumSoldItems();
		this.forSaleCount = market.getNumForSaleItems();
	}

	Object onActionFromShopForSale() {
		marketPage.setCategory(Category.none);
		return marketPage;
	}

}
