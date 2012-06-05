package com.ngdb.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.User;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class UserMenu {

	@Inject
	private CurrentUser currentUser;

	public Long getUserId() {
		return currentUser.getUserId();
	}

	public String getUsername() {
		return currentUser.getUsername();
	}

	public User getUser() {
		return currentUser.getUser();
	}

	Object onActionFromLogout() {
		currentUser.logout();
		return Index.class;
	}

	public int getNumArticlesInCollection() {
		return getUser().getArticlesInCollection().size();
	}

	public int getNumArticlesInShop() {
		return getUser().getShopItems().size();
	}

	public int getNumArticlesInWishList() {
		return getUser().getWishes().size();
	}

}
