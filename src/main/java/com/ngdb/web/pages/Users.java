package com.ngdb.web.pages;

import static org.hibernate.criterion.Order.asc;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.user.User;

public class Users {

	@Property
	private User user;

	@Property
	private Collection<User> users;

	@Inject
	private Session session;

	@SetupRender
	void init() {
		this.users = session.createCriteria(User.class).addOrder(asc("login")).list();
	}
}
