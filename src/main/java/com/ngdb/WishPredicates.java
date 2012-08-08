package com.ngdb;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.Wish;

public class WishPredicates {

	public static class PlatformPredicate implements Predicate<Wish> {
		private Long id;

		public PlatformPredicate(Platform platform) {
			this.id = platform.getId();
		}

		@Override
		public boolean apply(Wish wish) {
			Platform platform = wish.getArticle().getPlatform();
			Long platformId = platform.getId();
			return id.equals(platformId);
		}

	}

    public static class OriginPredicate implements Predicate<Wish> {
		private Long id;

		public OriginPredicate(Origin origin) {
			this.id = origin.getId();
		}

		@Override
		public boolean apply(Wish wish) {
			Origin origin = wish.getArticle().getOrigin();
			Long originId = origin.getId();
			return id.equals(originId);
		}

	}

}