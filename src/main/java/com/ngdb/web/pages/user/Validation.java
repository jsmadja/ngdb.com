package com.ngdb.web.pages.user;

import com.ngdb.entities.user.Token;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.TokenService;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class Validation {

	private static final Logger logger = LoggerFactory.getLogger(Validation.class);

	@Inject
	private TokenService tokenService;

	@Persist
	private String email;

	@Persist
	private String tokenValue;

	Object onActivate( //
			@RequestParameter(value = "email", allowBlank = true) String email, //
			@RequestParameter(value = "token", allowBlank = true) String tokenValue) {
		if (isNotBlank(email)) {
			this.email = email;
		}
		if (isNotBlank(tokenValue)) {
			this.tokenValue = tokenValue;
		}
		return null;
	}

	@SetupRender
	@CommitAfter
	void render() {
		if (isBlank(email) || isBlank(tokenValue)) {
			return;
		}
		Token token = tokenService.findToken(tokenValue);
		if (token == null || !token.getUser().getEmail().equals(email)) {
			logger.info("Someone tried to validate an invalid token, email:" + email);
			return;
		}
		User user = token.getUser();
		tokenService.destroyToken(user);
	}
}
