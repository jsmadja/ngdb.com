package com.ngdb.web.pages.article;

import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.State;

public class SellItemHistory {

	@Property
	private String stateName;

	@Property
	private int forSaleQuantity;

	@Property
	private double averagePrice;

	@Property
	private double maxPrice;

	@Property
	private double minPrice;

	public SellItemHistory(Article article, State state) {
		this.stateName = state.getTitle();
		this.forSaleQuantity = article.getAvailableCopyInState(state);
		this.averagePrice = article.getAveragePriceInState(state);
		this.maxPrice = article.getMaxPriceInState(state);
		this.minPrice = article.getMaxPriceInState(state);
	}
}
