package com.ngdb.web.components;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class LoginForm {

	@Property
	private String login;

	@Property
	private String password;

	@Inject
	private CurrentUser userSession;

	@Component(id = "loginForm")
	private Form form;

	void onSuccess() {
		try {
			userSession.login(login, password);
		} catch (AuthenticationException authException) {
			form.recordError("Invalid credentials");
		}
	}

	public User getUser() {
		return userSession.getUser();
	}

}
