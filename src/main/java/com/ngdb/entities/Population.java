package com.ngdb.entities;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.user.User;

public class Population {

	@Inject
	private Session session;

	public User findById(Long id) {
		return (User) session.load(User.class, id);
	}

	public Collection<User> findEverybody() {
		return session.createCriteria(User.class).addOrder(asc("login")).list();
	}

	public Long getNumUsers() {
		return (Long) session.createCriteria(User.class).setProjection(count("id")).uniqueResult();
	}

	public User findByLogin(String login) {
		return (User) session.createCriteria(User.class).add(eq("login", login)).uniqueResult();
	}

	public void addUser(User user) {
		session.persist(user);
	}

	public boolean exists(String login) {
		return findByLogin(login) != null;
	}

}
