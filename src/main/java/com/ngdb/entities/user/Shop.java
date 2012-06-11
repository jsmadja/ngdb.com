package com.ngdb.entities.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.google.common.collect.Collections2;
import com.ngdb.Predicates;
import com.ngdb.entities.shop.ShopItem;

@Embeddable
public class Shop {

	@OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
	private Set<ShopItem> shopItems;

	public Set<ShopItem> getShopItems() {
		return Collections.unmodifiableSet(shopItems);
	}

	public Collection<ShopItem> getShopItemsToSell() {
		return Collections2.filter(shopItems, Predicates.shopItemsForSale);
	}

	public int getNumArticlesToSell() {
		return getShopItemsToSell().size();
	}

	public boolean contains(ShopItem shopItem) {
		return shopItems.contains(shopItem);
	}
}
