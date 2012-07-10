package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.web.services.MailService;

public class Contact {

	@Property
	@Validate("required")
	private String title;

	@Property
	private String comment;

	@Persist
	@Property
	private String message;

	@Inject
	private MailService mailService;

	@Inject
	private Population population;

	public void onActivate() {

	}

	public boolean onActivate(String title) {
		this.title = title;
		return true;
	}

	public Object onSuccess() {
		mailService.sendMail("julien.smadja+neogeodb@gmail.com", "[CONTACT] " + comment, title);
		message = "Your comment has been successfully sent";
		return this;
	}

}
