package com.ngdb.web.pages.user;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.User;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.TokenService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

public class ChangePassword {

	@Inject
	private TokenService tokenService;

	@Persist
	private String email;

	@Persist
	private String tokenValue;

	@Persist
	private String login;

	@Property
	@Validate("required,minLength=6")
	private String password;

	@Property
	@Validate("required,minLength=6")
	private String confirmPassword;

	@InjectComponent
	private Form changePasswordForm;

	@Inject
	private CurrentUser userSession;

	@Inject
	private Session session;

	@Inject
	private Population population;

	@Property
	private User user;

	Object onActivate( //
			@RequestParameter(value = "login", allowBlank = true) String login, //
			@RequestParameter(value = "email", allowBlank = true) String email, //
			@RequestParameter(value = "token", allowBlank = true) String tokenValue) {

		if (StringUtils.isBlank(email) && StringUtils.isBlank(tokenValue) && StringUtils.isBlank(login)) { // cas du submit formulaire
			return null;
		}

		if (StringUtils.isBlank(email) || StringUtils.isBlank(tokenValue) || StringUtils.isBlank(login)) { // cas de l'appel anormal
			return Index.class;
		}
		this.user = population.findByLogin(login);
		tokenService.destroyToken(user);
		return null;
	}

	void onValidateFromChangePasswordForm() {
		if (!password.equals(confirmPassword)) {
			changePasswordForm.recordError("Password are differents");
		}
	}

	@CommitAfter
	Object onSuccess(User user) {
		user.setPassword(new Sha1Hash(password).toBase64());
		session.flush();
		userSession.login(user.getLogin(), password);
		return Index.class;
	}
}
