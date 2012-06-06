package com.ngdb.entities.user;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Functions.fromCollectionObjectToArticle;
import static com.ngdb.Predicates.isGame;
import static com.ngdb.Predicates.isHardware;
import static javax.persistence.FetchType.LAZY;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.ngdb.entities.article.Article;

@Embeddable
public class ArticleCollection {

	@OneToMany(mappedBy = "owner", fetch = LAZY)
	private Set<CollectionObject> collection;

	public boolean contains(Article article) {
		for (CollectionObject collectionObject : collection) {
			Long searchId = article.getId();
			Long idInCollection = collectionObject.getArticle().getId();
			if (searchId.equals(idInCollection)) {
				return true;
			}
		}
		return false;
	}

	public void addInCollection(CollectionObject collectionObject) {
		collection.add(collectionObject);
	}

	public Collection<Article> getArticles() {
		return transform(collection, fromCollectionObjectToArticle);
	}

	public Collection<Article> getGames() {
		return filter(getArticles(), isGame);
	}

	public Collection<Article> getHardwares() {
		return filter(getArticles(), isHardware);
	}

	public int getNumArticles() {
		return collection.size();
	}

}
