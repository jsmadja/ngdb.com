package com.ngdb.domain;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private static final List<User> users = new ArrayList<User>();

	static {
		users.add(new User("anzymus"));
		users.add(new User("takou"));
	}

	public static List<User> findAll() {
		return users;
	}

}
