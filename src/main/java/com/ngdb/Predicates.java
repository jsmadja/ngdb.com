package com.ngdb;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
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

	public static Predicate<Game> keepAesOnly = new Predicate<Game>() {
		@Override
		public boolean apply(Game game) {
			return "AES".equals(game.getPlatform().getName());
		}
	};

	public static Predicate<Game> keepMvsOnly = new Predicate<Game>() {
		@Override
		public boolean apply(Game game) {
			return "MVS".equals(game.getPlatform().getName());
		}
	};

	public static Predicate<Game> keepCdOnly = new Predicate<Game>() {
		@Override
		public boolean apply(Game game) {
			return "CD".equals(game.getPlatform().getName());
		}
	};

	public static Predicate<Game> keepJapanOnly = new Predicate<Game>() {
		@Override
		public boolean apply(Game game) {
			return "Japan".equals(game.getOrigin().getTitle());
		}
	};

	public static Predicate<Game> keepUsaOnly = new Predicate<Game>() {
		@Override
		public boolean apply(Game game) {
			return "USA".equals(game.getOrigin().getTitle());
		}
	};

	public static class PlatformPredicate implements Predicate<Article> {
		private String name;

		public PlatformPredicate(Platform platform) {
			this.name = platform.getName();
		}

		@Override
		public boolean apply(Article article) {
			Platform platform = article.getPlatform();
			String platformName = platform.getName();
			return name.equals(platformName);
		}

	}

	public static class OriginPredicate implements Predicate<Article> {
		private Long id;

		public OriginPredicate(Origin origin) {
			this.id = origin.getId();
		}

		@Override
		public boolean apply(Article article) {
			Origin origin = article.getOrigin();
			Long originId = origin.getId();
			return id.equals(originId);
		}

	}

	public static class PublisherPredicate implements Predicate<Article> {
		private String name;

		public PublisherPredicate(Publisher publisher) {
			this.name = publisher.getName();
		}

		@Override
		public boolean apply(Article article) {
			Publisher publisher = article.getPublisher();
			if (publisher == null) {
				return false;
			}
			String publisherName = publisher.getName();
			return name.equalsIgnoreCase(publisherName);
		}

	}

}
