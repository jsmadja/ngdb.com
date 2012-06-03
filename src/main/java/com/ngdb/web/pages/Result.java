package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.UserSession;

public class Result {

	@Persist
	private Collection<Article> results;

	@Property
	private Article result;

	@Persist
	private String search;

	@Inject
	private UserSession userSession;

	@Inject
	private Session session;

	public void setResults(Collection<Article> results) {
		this.results = results;
	}

	public Collection<Article> getResults() {
		return results;
	}

	public boolean isGame() {
		return result instanceof Game;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

	public boolean isAddableToCollection() {
		return userSession.canAddToCollection(result);
	}

	@CommitAfter
	Object onActionFromCollection(Article article) {
		User user = userSession.getUser();
		CollectionObject collectionObject = new CollectionObject(user, article);
		session.persist(collectionObject);
		user.addInCollection(collectionObject);
		return this;
	}

}
