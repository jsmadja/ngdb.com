package com.ngdb.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.User;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.infrastructure.UserSession;

public class UserMenu {

	@Inject
	private UserSession userSession;

	public Long getUserId() {
		return userSession.getUserId();
	}

	public String getUsername() {
		return userSession.getUsername();
	}

	public User getUser() {
		return userSession.getUser();
	}

	Object onActionFromLogout() {
		userSession.logout();
		return Index.class;
	}

	public int getNumArticlesInCollection() {
		return userSession.getUser().getArticlesInCollection().size();
	}

}
