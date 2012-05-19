package com.ngdb.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Article;
import com.ngdb.entities.User;
import com.ngdb.web.services.UserService;

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
