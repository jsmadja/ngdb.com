package com.ngdb.entities.shop;

import javax.persistence.Entity;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.user.User;

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
