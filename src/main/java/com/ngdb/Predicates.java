package com.ngdb;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.shop.ShopItem;

public class Predicates {

	public static Predicate<ShopItem> shopItemsForSale = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem shopItem) {
			return !shopItem.isSold();
		}
	};

	public static Predicate<Article> isGame = new Predicate<Article>() {
		@Override
		public boolean apply(Article input) {
			return input.getType().equals(Game.class);
		}
	};

	public static Predicate<Article> isHardware = new Predicate<Article>() {
		@Override
		public boolean apply(Article input) {
			return input.getType().equals(Hardware.class);
		}
	};

	public static Predicate<Article> releasedThisMonth = new Predicate<Article>() {

		public boolean apply(Article game) {
			if (game == null) {
				return false;
			}
			if (game.getReleaseDate() == null) {
				return false;
			}
			final int month = new DateTime().getMonthOfYear();
			return (game.getReleaseDate().getMonth() + 1) == month;
		}
	};

}
