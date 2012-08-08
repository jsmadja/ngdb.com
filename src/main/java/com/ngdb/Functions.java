package com.ngdb;

import com.google.common.base.Function;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.CollectionObject;

import javax.annotation.Nullable;

public class Functions {

	public static Function<CollectionObject, Article> fromCollectionObjectToArticle = new Function<CollectionObject, Article>() {
		@Override
		public Article apply(CollectionObject input) {
			return input.getArticle();
		}
	};

    public static Function<Wish, Article> fromWishToArticle = new Function<Wish, Article>() {
        @Override
        public Article apply(@Nullable Wish input) {
            return input.getArticle();
        }
    };
}
