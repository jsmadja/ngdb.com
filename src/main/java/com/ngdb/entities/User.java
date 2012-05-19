package com.ngdb.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String login;

	@OneToMany(mappedBy = "wisher")
	private Set<Wish> wishList;

	@OneToMany(mappedBy = "owner")
	private Set<CollectionObject> collection;

	@OneToMany(mappedBy = "seller")
	private Set<ShopItem> shop;

	public User() {
	}

	public User(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public boolean canAddInCollection(Article article) {
		if (collection == null) {
			return true;
		}
		for (CollectionObject collectionObject : collection) {
			if (article.equals(collectionObject.getArticle())) {
				return false;
			}
		}
		return true;
	}

	public boolean canSell(Article article) {
		if (collection == null) {
			return false;
		}
		for (CollectionObject collectionObject : collection) {
			if (article.equals(collectionObject.getArticle())) {
				return true;
			}
		}
		return false;
	}

	public boolean canWish(Article article) {
		if (wishList == null) {
			return true;
		}
		for (Wish wish : wishList) {
			if (article.equals(wish.getArticle())) {
				return false;
			}
		}
		return false;
	}
}
