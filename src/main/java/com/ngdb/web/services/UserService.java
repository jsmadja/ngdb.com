package com.ngdb.web.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.User;

public class UserService {

	@Inject
	private Session session;

	public User getCurrentUser() {
		return (User) session.load(User.class, 1L);
	}

	public boolean isLoggedUser(User user) {
		return getCurrentUser().equals(user);
	}

	public Long getUserId() {
		return getCurrentUser().getId();
	}

	public String getUsername() {
		return getCurrentUser().getLogin();
	}

}
