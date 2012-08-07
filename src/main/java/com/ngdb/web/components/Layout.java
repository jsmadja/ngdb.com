package com.ngdb.web.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Layout {

    @Property
    @Parameter
    private Boolean showLeftContent;

	@Inject
	private CurrentUser userSession;

	@Inject
	private Request request;

    @SetupRender
    public void init() {
        if(showLeftContent == null) {
            showLeftContent = true;
        }
    }

	public User getUser() {
		return userSession.getUser();
	}

	public boolean isProductionServer() {
		return "true".equalsIgnoreCase(System.getProperty("production"));
	}

}