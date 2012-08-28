package com.ngdb.web.pages.user;

import com.ngdb.entities.user.User;
import org.apache.tapestry5.annotations.Persist;

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
