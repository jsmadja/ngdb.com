package com.ngdb.domain;

import java.util.List;

public class User extends AbstractEntity {

	private String login;
	private WishList wishes = new WishList();
	private Collection collection = new Collection();
	private Shop shop = new Shop();

	public User(String login) {
		super();
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public List<Article> getWishes() {
		return wishes.getWishes();
	}

	public List<ShopItem> getShopItems() {
		return shop.getShopItems();
	}

	public void addToCollection(Article article) {
		collection.add(article);
	}

	public java.util.Collection<Article> getGamesInCollection() {
		return collection.getGames();
	}

	public void addToWishList(Article article) {
		wishes.add(article);
	}

}
