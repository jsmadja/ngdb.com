package com.ngdb.web.pages.user;

import org.apache.tapestry5.annotations.Persist;

import com.ngdb.entities.user.User;

public class ConfirmationPage {

	@Persist
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
