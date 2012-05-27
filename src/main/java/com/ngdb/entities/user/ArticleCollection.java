package com.ngdb.entities.user;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;

@Embeddable
public class ArticleCollection {

	@OneToMany(mappedBy = "owner")
	private Set<CollectionObject> collection;

	public boolean contains(Article article) {
		for (CollectionObject collectionObject : collection) {
			if (article.equals(collectionObject.getArticle())) {
				return true;
			}
		}
		return false;
	}

	public Collection<Article> getArticles() {
		Function<CollectionObject, Article> f = new Function<CollectionObject, Article>() {
			@Override
			public Article apply(CollectionObject input) {
				return input.getArticle();
			}
		};
		return Collections2.transform(collection, f);
	}

}
