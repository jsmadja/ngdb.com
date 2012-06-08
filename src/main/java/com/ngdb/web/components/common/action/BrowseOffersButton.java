package com.ngdb.web.components.common.action;

import static com.ngdb.web.Category.byArticle;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.web.pages.Market;

public class BrowseOffersButton {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private boolean showText;

	@InjectPage
	private Market market;

	Object onActionFromBuy(Article article) {
		market.setId(article.getId());
		market.setCategory(byArticle);
		return market;
	}

}
