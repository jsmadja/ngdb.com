package com.ngdb.entities.user;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.ngdb.entities.article.Article;

@Embeddable
public class ArticleCollection {

	@OneToMany(mappedBy = "owner")
	private Set<CollectionObject> collection;

	public boolean contains(Article article) {
		for (CollectionObject collectionObject : collection) {
			if (article.equals(collectionObject.getArticle())) {
				return false;
			}
		}
		return true;
	}

}
