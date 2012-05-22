package com.ngdb.web.services.domain;

import static org.hibernate.criterion.Order.asc;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.user.User;

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

	public Collection<User> findAll() {
		return session.createCriteria(User.class).addOrder(asc("login")).list();
	}

}
