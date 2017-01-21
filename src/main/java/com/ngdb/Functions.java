package com.ngdb;

import com.google.common.base.Function;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.CollectionObject;

public class Functions {

	public static Function<CollectionObject, Article> fromCollectionObjectToArticle = new Function<CollectionObject, Article>() {
		@Override
		public Article apply(CollectionObject input) {
			return input.getArticle();
		}
	};

}
