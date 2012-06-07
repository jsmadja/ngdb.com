package com.ngdb.web.pages.article;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.web.services.infrastructure.CurrentUser;

public abstract class ArticleView {

	@Inject
	protected CurrentUser userSession;

	public boolean isAddableToCollection() {
		return userSession.canAddToCollection(getArticle());
	}

	public boolean isBuyable() {
		return getArticle().isBuyable();
	}

	public boolean isSellable() {
		return userSession.canSell(getArticle());
	}

	public boolean isWishable() {
		return userSession.canWish(getArticle());
	}

	protected abstract Article getArticle();

}
