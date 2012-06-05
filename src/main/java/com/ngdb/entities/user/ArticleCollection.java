package com.ngdb.entities.user;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.google.common.collect.Collections2;
import com.ngdb.Functions;
import com.ngdb.entities.article.Article;

@Embeddable
public class ArticleCollection {

	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
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
		return Collections2.transform(collection, Functions.fromCollectionObjectToArticle);
	}

	public void addInCollection(CollectionObject collectionObject) {
		collection.add(collectionObject);
	}

}
