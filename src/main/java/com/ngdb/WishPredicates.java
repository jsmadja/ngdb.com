package com.ngdb;

import com.google.common.base.Predicate;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.Wish;

public class WishPredicates {

	public static class PlatformPredicate implements Predicate<Wish> {
		private Predicates.PlatformPredicate predicate;

		public PlatformPredicate(Platform platform) {
			this.predicate = new Predicates.PlatformPredicate(platform);
		}

		@Override
		public boolean apply(Wish wish) {
            return predicate.apply(wish.getArticle());
		}

	}

    public static class OriginPredicate implements Predicate<Wish> {
		private Predicates.OriginPredicate predicate;

		public OriginPredicate(Origin origin) {
			this.predicate = new Predicates.OriginPredicate(origin);
		}

		@Override
		public boolean apply(Wish wish) {
			return predicate.apply(wish.getArticle());
		}
	}

}