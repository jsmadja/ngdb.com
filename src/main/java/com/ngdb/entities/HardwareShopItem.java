package com.ngdb.entities;

import javax.persistence.Entity;

@Entity
public class HardwareShopItem extends ShopItem {

	public HardwareShopItem() {
	}

	public HardwareShopItem(Picture mainPicture, Hardware hardware, double price, State state, String details, User user) {
		super(mainPicture, hardware, price, state, details, user);
	}

}
