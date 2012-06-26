package com.ngdb.entities.article.element;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
		if (url == null) {
			return EMPTY.getUrl();
		}
		if (isWatermarked()) {
			return toWatermarkedUrl();
		}
		return url;
	}

	public String toWatermarkedUrl() {
		StringBuilder watermarkedUrl = new StringBuilder(url);
		watermarkedUrl.insert(url.lastIndexOf("."), "_wm");
		return watermarkedUrl.toString();
	}

	private boolean isWatermarked() {
		return new File(toWatermarkedUrl()).exists();
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setShopItem(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	@Override
	public String toString() {
		return url;
	}

}
