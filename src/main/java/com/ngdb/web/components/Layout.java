package com.ngdb.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.UserSession;

public class Layout {

	@Inject
	private UserSession userSession;

	public User getUser() {
		return userSession.getUser();
	}

}