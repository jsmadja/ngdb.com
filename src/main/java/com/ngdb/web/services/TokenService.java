package com.ngdb.web.services;

import com.ngdb.entities.user.Token;
import com.ngdb.entities.user.User;
import org.hibernate.Session;

import javax.inject.Inject;

import static org.hibernate.criterion.Restrictions.eq;

public class TokenService {

	@Inject
	private Session session;

	public Token createToken(User user) {
		destroyToken(user);
		Token token = new Token(user);
		session.persist(token);
		return token;
	}

	public Token findToken(String value) {
		return (Token) session.createCriteria(Token.class).add(eq("value", value)).uniqueResult();
	}

	public void destroyToken(User user) {
		Token token = findToken(user);
		if (token != null) {
			session.delete(token);
		}
	}

	private Token findToken(User user) {
		return (Token) session.createCriteria(Token.class).add(eq("user", user)).uniqueResult();
	}

}