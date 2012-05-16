package com.ngdb.domain;

public class ShopItem extends AbstractEntity {

	private Picture mainPicture;
	private double price;
	protected Article article;

	public ShopItem(Picture mainPicture, Article article, double price) {
		this.mainPicture = mainPicture;
		this.article = article;
		this.price = price;
	}

	public double getPrice() {
		return price;
	}

	public String getTitle() {
		return article.getTitle();
	}

	public Picture getMainPicture() {
		return mainPicture;
	}

}
