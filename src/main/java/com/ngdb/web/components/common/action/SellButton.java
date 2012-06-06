package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.web.pages.shop.ShopItemUpdate;

public class SellButton {

	@Property
	@Parameter
	protected Article article;

	@Property
	@Parameter
	protected boolean showText;

	@InjectPage
	private ShopItemUpdate shopItemUpdate;

	Object onActionFromSell(Article article) {
		shopItemUpdate.setArticle(article);
		return shopItemUpdate;
	}

}
