package com.ngdb;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.ShopItem;

public class ShopItemPredicates {

	public static class PlatformPredicate implements Predicate<ShopItem> {
		private Long id;

		public PlatformPredicate(Platform platform) {
			this.id = platform.getId();
		}

		@Override
		public boolean apply(ShopItem shopItem) {
			Platform platform = shopItem.getArticle().getPlatform();
			Long platformId = platform.getId();
			return id.equals(platformId);
		}

	}

	public static class OriginPredicate implements Predicate<ShopItem> {
		private Long id;

		public OriginPredicate(Origin origin) {
			this.id = origin.getId();
		}

		@Override
		public boolean apply(ShopItem shopItem) {
			Origin origin = shopItem.getArticle().getOrigin();
			Long originId = origin.getId();
			return id.equals(originId);
		}

	}

	public static final Predicate<ShopItem> hardwaresForSale = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return !input.isSold() && input.getArticle().getType().equals(Hardware.class);
		}
	};

	public static final Predicate<ShopItem> gamesForSale = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return !input.isSold() && input.getArticle().getType().equals(Game.class);
		}
	};

	public static Predicate<ShopItem> isGameShopItem = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return input.getArticle().getType().equals(Game.class);
		}
	};

	public static Predicate<ShopItem> isHardwareShopItem = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return input.getArticle().getType().equals(Hardware.class);
		}
	};

}