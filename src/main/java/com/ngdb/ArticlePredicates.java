package com.ngdb;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;

public class ArticlePredicates {

	public static class PlatformPredicate implements Predicate<Article> {
		private Long id;

		public PlatformPredicate(Platform platform) {
			this.id = platform.getId();
		}

		@Override
		public boolean apply(Article article) {
			Platform platform = article.getPlatform();
			Long platformId = platform.getId();
			return id.equals(platformId);
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

}