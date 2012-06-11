package com.ngdb.base;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.State;

public class SellItemHistory {

	private String stateName;

	private int forSaleQuantity;

	private double averagePrice;

	private double maxPrice;

	private double minPrice;

	public SellItemHistory(Article article, State state) {
		this.stateName = state.getTitle();
		this.forSaleQuantity = article.getAvailableCopyInState(state);
		this.averagePrice = article.getAveragePriceInState(state);
		this.maxPrice = article.getMaxPriceInState(state);
		this.minPrice = article.getMaxPriceInState(state);
	}

	public String getStateName() {
		return stateName;
	}

	public int getForSaleQuantity() {
		return forSaleQuantity;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public double getMinPrice() {
		return minPrice;
	}

}
