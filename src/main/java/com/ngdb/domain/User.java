package com.ngdb.domain;

import java.util.Date;

public class User implements BaseEntity {

	private Date creationDate;
	private Long id;
	private String login;

	public User(String login) {
		this.creationDate = new Date();
		this.login = login;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	public String getLogin() {
		return login;
	}

}
