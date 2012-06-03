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
		return "88.179.37.215".equals(request.getRemoteHost());
	}

}