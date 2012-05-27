package com.ngdb.entities.shop;

import javax.persistence.Entity;

import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.user.User;

@Entity
public class HardwareShopItem extends ShopItem {

	public HardwareShopItem() {
	}

	public HardwareShopItem(Picture mainPicture, Hardware hardware, double price, State state, String details, User user) {
		super(mainPicture, hardware, price, state, details, user);
	}

}
