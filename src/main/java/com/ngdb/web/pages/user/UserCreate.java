package com.ngdb.web.pages.user;

import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.entities.user.User;

public class UserCreate {

	@Inject
	private Population population;

	@Property
	@Validate("required")
	private String login;

	@Property
	@Validate("required,email,maxLength=255")
	private String email;

	@Property
	@Validate("required,minLength=6")
	private String password;

	@Property
	@Validate("required,minLength=6")
	private String confirmPassword;

	@InjectComponent("userForm")
	private Form form;

	@InjectPage
	private ConfirmationPage confirmationPage;

	void onValidateFromUserForm() {
		if (!password.equals(confirmPassword)) {
			form.recordError("Password are differents");
		}
		if (population.exists(login)) {
			form.recordError("Choose another login");
		}
	}

	@CommitAfter
	Object onSuccess() {
		User user = new User(login, email);
		user.setPassword(new Sha1Hash(password).toBase64());
		population.addUser(user);
		confirmationPage.setUser(user);
		return confirmationPage;
	}

}
