package com.ngdb.entities;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.user.Token;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.TokenService;

public class Population {

	@Inject
	private Session session;

	@Inject
	private TokenService tokenService;

	@Inject
	private MailService mailService;

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
		envoyerMailConfirmationInscription(user);
	}

	private void envoyerMailConfirmationInscription(User user) {
		Token token = tokenService.createToken(user);
		Map<String, String> params = new HashMap<String, String>();
		params.put("recipient", user.getEmail());
		params.put("url", "http://fluxx:8080/user/validation?token=" + token.getValue() + "&email=" + user.getEmail());
		mailService.sendMail(user, "subscription", params);
	}

	public boolean exists(String login) {
		return findByLogin(login) != null;
	}

}
