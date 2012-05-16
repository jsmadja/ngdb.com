package com.ngdb.domain;

import java.util.ArrayList;
import java.util.List;

public class Users {

	private static final List<User> users = new ArrayList<User>();

	static {
		users.add(new User("anzymus"));
		users.add(new User("takou"));
		users.add(new User("chacha"));
		users.add(new User("neoforever"));
	}

	public static List<User> findAll() {
		return users;
	}

}
