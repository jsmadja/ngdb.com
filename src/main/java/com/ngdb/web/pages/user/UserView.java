package com.ngdb.web.pages.user;

import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;

public class UserView {

	@Property
	private User user;

	@Property
	private Wish wish;

	@Property
	private Article article;

	@Property
	private ShopItem shopItem;

	void onActivate(User user) {
		this.user = user;
	}

}
