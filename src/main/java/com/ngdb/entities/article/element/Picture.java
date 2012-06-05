package com.ngdb.entities.article.element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;

@Entity
public class Picture extends AbstractEntity {

	public static final Picture EMPTY = new Picture("/ngdb/unknown.png");

	@Column(nullable = false)
	private String url;

	@ManyToOne(optional = true)
	private Article article;

	@ManyToOne(optional = true)
	private ShopItem shopItem;

	Picture() {
	}

	public Picture(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url == null ? EMPTY.getUrl() : url;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

}
