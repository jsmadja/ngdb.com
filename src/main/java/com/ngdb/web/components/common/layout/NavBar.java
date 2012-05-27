package com.ngdb.web.components.common.layout;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.web.services.infrastructure.UserSession;

public class NavBar {

	@Inject
	private UserSession userSession;

	public Long getUserId() {
		return userSession.getUserId();
	}

	public String getUsername() {
		return userSession.getUsername();
	}

}
