package com.ngdb.web.components;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.User;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.infrastructure.UserSession;

public class LoginForm {

	@Property
	private String login;

	@Property
	private String password;

	@Inject
	private UserSession userSession;

	@Component(id = "loginForm")
	private Form form;

	Object onSuccess() {
		try {
			userSession.login(login, password);
			return Index.class;
		} catch (AuthenticationException authException) {
			form.recordError("Invalid credentials");
			return this;
		}
	}

	public User getUser() {
		return userSession.getUser();
	}

}