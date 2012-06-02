package com.ngdb.web.components;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.UserSession;

public class Layout {

	@Inject
	private UserSession userSession;

	@Inject
	private Request request;

	public User getUser() {
		return userSession.getUser();
	}

	public boolean isProductionServer() {
		System.err.println(request.getPath());
		System.err.println(request.getContextPath());
		System.err.println(request.getRemoteHost());
		System.err.println(request.getServerName());
		return true;
	}

}