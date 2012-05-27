package com.ngdb.web.components.common.layout;

import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.pages.Market;
import com.ngdb.web.pages.shop.ShopItemView;

public class Menu {

	@Property
	private List<ShopItem> shopItems;

	@Property
	private ShopItem shopItem;

	@Property
	private Long gameCount;

	@Property
	private Long hardwareCount;

	@Property
	private Long wishListCount;

	@Property
	private Long userCount;

	@Property
	private Long forSaleCount;

	@Property
	private Long soldCount;

	@InjectPage
	private Market marketPage;

	@InjectPage
	private ShopItemView shopItemView;

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

	@SetupRender
	public void init() {
		this.shopItems = market.findLastForSaleItems();
		this.gameCount = gameFactory.getNumGames();
		this.userCount = population.getNumUsers();
		this.hardwareCount = hardwareFactory.getNumHardwares();
		this.wishListCount = wishBox.getNumWishes();
		this.forSaleCount = market.getNumForSaleItems();
		this.soldCount = market.getNumSoldItems();
	}

	public String getBySoldDate() {
		return "bySoldDate";
	}

	// Object onActionFromShopForSale() {
	// marketPage.setCategory(none);
	// return marketPage;
	// }
	//
	// Object onActionFromMore() {
	// marketPage.setCategory(none);
	// return marketPage;
	// }
	//
	// Object onActionFromShopSold() {
	// marketPage.setCategory(bySoldDate);
	// return marketPage;
	// }
	//
	// Object onActionFromShopItem(ShopItem shopItem) {
	// shopItemView.setShopItem(shopItem);
	// return shopItemView;
	// }

}
