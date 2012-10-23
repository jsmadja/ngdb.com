package com.ngdb.web.components;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

public class Layout {

    @Property
    @Parameter
    private Boolean showLeftContent;

    @Inject
    private CurrentUser userSession;

    @Inject
    private Request request;

    @Property
    @Parameter
    private String title;

    @Property
    @Parameter
    private String description;

    @SetupRender
    public void init() {
        if (showLeftContent == null) {
            showLeftContent = true;
        }
    }

    public User getUser() {
        return userSession.getUser();
    }

}