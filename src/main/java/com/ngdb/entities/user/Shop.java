package com.ngdb.entities.user;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.ngdb.entities.shop.ShopItem;

@Embeddable
public class Shop {

	@OneToMany(mappedBy = "seller")
	private Set<ShopItem> shop;

}
