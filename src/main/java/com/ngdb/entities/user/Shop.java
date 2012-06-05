package com.ngdb.entities.user;

import java.util.Collections;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.ngdb.entities.shop.ShopItem;

@Embeddable
public class Shop {

	@OneToMany(mappedBy = "seller", fetch = FetchType.EAGER)
	private Set<ShopItem> shopItems;

	public Set<ShopItem> getShopItems() {
		return Collections.unmodifiableSet(shopItems);
	}

}
