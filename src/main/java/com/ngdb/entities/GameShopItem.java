package com.ngdb.entities;

import javax.persistence.Entity;

@Entity
public class GameShopItem extends ShopItem {

	public GameShopItem() {
	}

	public GameShopItem(Picture mainPicture, Game game, double price, State state, String details, User user) {
		super(mainPicture, game, price, state, details, user);
	}

	public String getTitle() {
		Game game = (Game) getArticle();
		return game.getTitle() + " (" + game.getPlatform().getName() + " - " + game.getOrigin().getTitle() + ")";
	}
}
