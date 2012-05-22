package com.ngdb.web.pages.article;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.domain.UserService;

public abstract class ArticleView {

	@Inject
	protected UserService userService;

	public boolean isAddableToCollection() {
		User user = userService.getCurrentUser();
		return user.canAddInCollection(getArticle());
	}

	public boolean isBuyable() {
		return getArticle().isBuyable();
	}

	public boolean isSellable() {
		User user = userService.getCurrentUser();
		return user.canSell(getArticle());
	}

	public boolean isWishable() {
		User user = userService.getCurrentUser();
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

}
