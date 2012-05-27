package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.User;

public class Users {

	@Property
	private User user;

	@Property
	private Collection<User> users;

	@Inject
	private Population population;

	@SetupRender
	void init() {
		this.users = population.findEverybody();
	}
}
