package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;

public class Users {

    @Property
    private User user;

    @Property
    private Collection<User> users;

    @Inject
    private Population population;

    @Persist
    @Property
    private BeanModel<User> model;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @SetupRender
    void init() {
        this.users = population.findEverybody();
        model = beanModelSource.createDisplayModel(User.class, messages);
        model.get("login").sortable(true);
        model.get("creationDate").sortable(true);
        model.include("login", "creationDate", "lastLoginDate");
    }

    public String getByUser() {
        return Filter.byUser.name();
    }

}
