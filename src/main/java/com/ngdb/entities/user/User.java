package com.ngdb.entities.user;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
public class User extends AbstractEntity {

	@Column(nullable = false)
	private String login;

	@Embedded
	private WishList wishList;

	@Embedded
	private ArticleCollection collection;

	@Embedded
	private Shop shop;

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
		return !collection.contains(article);
	}

	public boolean canSell(Article article) {
		if (collection == null) {
			return false;
		}
		return collection.contains(article);
	}

	public boolean canWish(Article article) {
		if (wishList == null) {
			return true;
		}
		return !wishList.contains(article);
	}

}
