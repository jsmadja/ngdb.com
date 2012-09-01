package com.ngdb;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.ShopItem;

public class ShopItemPredicates {

	public static class PlatformPredicate implements Predicate<ShopItem> {
		private Predicates.PlatformPredicate predicate;

		public PlatformPredicate(Platform platform) {
			this.predicate =  new Predicates.PlatformPredicate(platform);
		}

		@Override
		public boolean apply(ShopItem shopItem) {
            return predicate.apply(shopItem.getArticle());
		}
	}

    public static class ArticlePredicate implements Predicate<ShopItem> {
        private Long id;

        public ArticlePredicate(Article article) {
            this.id = article.getId();
        }

        @Override
        public boolean apply(ShopItem shopItem) {
            Article article = shopItem.getArticle();
            Long articleId = article.getId();
            return id.equals(articleId);
        }
    }

	public static class OriginPredicate implements Predicate<ShopItem> {
		private Predicates.OriginPredicate predicate;

		public OriginPredicate(Origin origin) {
			this.predicate = new Predicates.OriginPredicate(origin);
		}

		@Override
		public boolean apply(ShopItem shopItem) {
			return predicate.apply(shopItem.getArticle());
		}
	}

	public static final Predicate<ShopItem> hardwaresForSale = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return !input.isSold() && !input.getArticle().isGame();
		}
	};

	public static final Predicate<ShopItem> gamesForSale = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return !input.isSold() && input.getArticle().isGame();
		}
	};

	public static Predicate<ShopItem> isGameShopItem = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return input.getArticle().isGame();
		}
	};

	public static Predicate<ShopItem> isHardwareShopItem = new Predicate<ShopItem>() {
		@Override
		public boolean apply(ShopItem input) {
			return !input.getArticle().isGame();
		}
	};

}