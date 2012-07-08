package com.ngdb;

import com.google.common.base.Function;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.user.CollectionObject;

public class Functions {

	public static final Function<Article, Game> fromGameToArticle = new Function<Article, Game>() {
		@Override
		public Game apply(Article input) {
			return (Game) input;
		}
	};

	public static final Function<Article, Hardware> fromHardwareToArticle = new Function<Article, Hardware>() {
		@Override
		public Hardware apply(Article input) {
			return (Hardware) input;
		}
	};

	public static Function<CollectionObject, Article> fromCollectionObjectToArticle = new Function<CollectionObject, Article>() {
		@Override
		public Article apply(CollectionObject input) {
			return input.getArticle();
		}
	};

	public static Function<Tag, String> fromTagToString = new Function<Tag, String>() {
		@Override
		public String apply(Tag input) {
			return input.getName();
		}
	};

}
