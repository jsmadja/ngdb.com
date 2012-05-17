package com.ngdb.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String login;

	@OneToMany(mappedBy = "user")
	private Set<WishList> wishList;

	@OneToMany(mappedBy = "user")
	private Set<Collection> collection;

	@OneToMany(mappedBy = "user")
	private Set<ShopItem> shop;

	public User() {
	}

	public User(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
