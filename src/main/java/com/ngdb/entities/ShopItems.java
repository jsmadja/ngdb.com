package com.ngdb.entities;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Embeddable
public class ShopItems {

	@OneToMany(mappedBy = "article")
	private Set<ShopItem> shopItems;

	public int getAvailableCopyCount() {
		return shopItems.size();
	}

	private Collection<ShopItem> getSealedItems() {
		return Collections2.filter(shopItems, keepSealedState);
	}

	public int getMintQuantity() {
		return getMintItems().size();
	}

	private Collection<ShopItem> getMintItems() {
		return Collections2.filter(shopItems, keepMintState);
	}

	public int getUsedQuantity() {
		return getUsedItems().size();
	}

	private Collection<ShopItem> getUsedItems() {
		return Collections2.filter(shopItems, keepUsedState);
	}

	public String getSealedAverage() {
		return getAverage(getSealedItems());
	}

	public String getSealedMax() {
		return getMax(getSealedItems());
	}

	public String getSealedMin() {
		return getMin(getSealedItems());
	}

	private String getMax(Collection<ShopItem> items) {
		Double max = Double.MIN_VALUE;
		for (ShopItem shopItem : items) {
			max = Math.max(max, shopItem.getPrice());
		}
		if (max.equals(Double.MIN_VALUE)) {
			return "";
		}
		return "$" + max;
	}

	private String getMin(Collection<ShopItem> items) {
		Double min = Double.MAX_VALUE;
		for (ShopItem shopItem : items) {
			min = Math.min(min, shopItem.getPrice());
		}
		if (min.equals(Double.MAX_VALUE)) {
			return "";
		}
		return "$" + min;
	}

	private String getAverage(Collection<ShopItem> items) {
		Double average = 0D;
		for (ShopItem shopItem : items) {
			average += shopItem.getPrice();
		}
		if (average.equals(0D)) {
			return "";
		}
		return "$" + (average / shopItems.size());
	}

	public String getMintAverage() {
		return getAverage(getMintItems());
	}

	public String getMintMax() {
		return getMax(getMintItems());
	}

	public String getMintMin() {
		return getMin(getMintItems());
	}

	public String getUsedAverage() {
		return getAverage(getUsedItems());
	}

	public String getUsedMax() {
		return getMax(getUsedItems());
	}

	public String getUsedMin() {
		return getMin(getUsedItems());
	}

	public int getSealedQuantity() {
		return getSealedItems().size();
	}

	@Transient
	private StateFilter keepSealedState = new StateFilter("Sealed");

	@Transient
	private StateFilter keepMintState = new StateFilter("Mint");

	@Transient
	private StateFilter keepUsedState = new StateFilter("Used");

	class StateFilter implements Predicate<ShopItem> {
		private String state;

		public StateFilter(String state) {
			this.state = state;
		}

		@Override
		public boolean apply(ShopItem shopItem) {
			if (shopItem == null) {
				return false;
			}
			return shopItem.isSold() && state.equals(shopItem.getState().getTitle());
		}
	}

}
