package com.ngdb.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GameShopItem extends ShopItem {

	public GameShopItem() {
	}

	public GameShopItem(Picture mainPicture, Game game, double price) {
		super(mainPicture, game, price);
	}

	public String getTitle() {
		Game game = (Game) article;
		return article.getTitle() + " (" + game.getPlatform().getName() + " - " + game.getOrigin().getTitle() + ")";
	}
}
