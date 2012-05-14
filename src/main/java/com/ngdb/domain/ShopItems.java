package com.ngdb.domain;

import java.util.ArrayList;
import java.util.List;

public class ShopItems {

	private static List<ShopItem> shopItems = new ArrayList<ShopItem>();

	static {
		shopItems.add(new ShopItem(Games.findAll().get(0).getMainPicture(), Games.findAll().get(0), 55));
		shopItems.add(new ShopItem(Picture.EMPTY, Games.findAll().get(1), 135));
		shopItems.add(new ShopItem(Games.findAll().get(2).getMainPicture(), Games.findAll().get(2), 50));
		shopItems.add(new ShopItem(Games.findAll().get(3).getMainPicture(), Games.findAll().get(3), 39));
		shopItems.add(new ShopItem(Games.findAll().get(4).getMainPicture(), Games.findAll().get(4), 29.99));
	}

	public static List<ShopItem> findAll() {
		return shopItems;
	}

}
