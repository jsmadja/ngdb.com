package com.ngdb.web.pages.article;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.UserSession;

public abstract class ArticleView {

	@Inject
	protected UserSession userSession;

	public boolean isAddableToCollection() {
		return userSession.canAddToCollection(getArticle());
	}

	public boolean isBuyable() {
		return getArticle().isBuyable();
	}

	public boolean isSellable() {
		User user = userSession.getUser();
		return user.canSell(getArticle());
	}

	public boolean isWishable() {
		User user = userSession.getUser();
		return user.canWish(getArticle());
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
