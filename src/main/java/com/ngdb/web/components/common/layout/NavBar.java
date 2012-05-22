package com.ngdb.web.components.common.layout;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.web.services.domain.UserService;

public class NavBar {

	@Inject
	private UserService userService;

	public Long getUserId() {
		return userService.getUserId();
	}

	public String getUsername() {
		return userService.getUsername();
	}

}
