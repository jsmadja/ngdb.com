package com.ngdb.web.pages.user;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ResetPassword {

	@Persist
	@Property
	@Validate("required")
	private String login;

	@Persist
	@Property
	@Validate("required,email")
	private String email;

	@Inject
	private Population population;

	@InjectComponent
	private Form resetPasswordForm;

	@Persist
	@Property
	private boolean resetPasswordLinkSent;

	void onValidateFromResetPasswordForm() {
		User user = population.findByLogin(login);
		if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
			resetPasswordForm.recordError("No user found for these login and email");
		}
	}

	@CommitAfter
	Object onSuccess() {
		User user = population.findByLogin(login);
		population.resetPasswordOf(user);
		resetPasswordLinkSent = true;
		return this;
	}

}
