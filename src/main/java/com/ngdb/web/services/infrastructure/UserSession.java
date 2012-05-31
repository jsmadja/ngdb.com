package com.ngdb.web.services.infrastructure;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.tynamo.security.services.SecurityService;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.Shop;
import com.ngdb.entities.user.User;

public class UserSession {

	@Inject
	private ApplicationStateManager applicationStateManager;

	@Inject
	private SecurityService securityService;

	@Inject
	private Population population;

	@Inject
	private Request request;

	public User login(String login, String password) {
		Subject currentUser = securityService.getSubject();
		doLogout(currentUser);

		UsernamePasswordToken token = new UsernamePasswordToken(login, password);
		token.setRememberMe(true);
		currentUser.login(token);

		User user = population.findByLogin(login);
		init(user);
		return user;
	}

	public void logout() {
		doLogout(securityService.getSubject());
	}

	private void doLogout(Subject currentUser) {
		if (securityService.isAuthenticated()) {
			currentUser.logout();
			try {
				request.getSession(false).invalidate();
			} catch (Exception e) {
			}
		}
	}

	public <T> void store(Class<T> valueType, T storedValue) {
		applicationStateManager.set(valueType, storedValue);
	}

	public <T> T get(Class<T> valueType) {
		return applicationStateManager.getIfExists(valueType);
	}

	public <T> boolean contains(Class<T> valueType) {
		return applicationStateManager.exists(valueType);
	}

	public boolean isLogged() {
		return securityService.isAuthenticated();
	}

	public void init(User user) {
		store(User.class, user);
	}

	public User getUser() {
		return get(User.class);
	}

	public boolean isLoggedUser(User user) {
		return getUser().equals(user);
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getLogin();
	}

	public Shop getShop() {
		return getUser().getShop();
	}

}
