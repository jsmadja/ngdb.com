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

	public int getNumAvailableCopy() {
		return getArticle().getAvailableCopyCount();
	}

	protected abstract Article getArticle();

	public String getByOrigin() {
		return "byOrigin";
	}

	public String getByReleaseDate() {
		return "byReleaseDate";
	}

	public String getByArticle() {
		return "byArticle";
	}

	public boolean getHasNoComments() {
		return getArticle().getComments().isEmpty();
	}

}
