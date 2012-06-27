package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class RemoveFromCollectionButton {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private boolean asButton;

	@Property
	@Parameter
	private String returnPage;

	@Inject
	private CurrentUser currentUser;

	@CommitAfter
	Object onActionFromRemoveCollection(Article article) {
		currentUser.removeFromCollection(article);
		return returnPage;
	}

	@CommitAfter
	Object onActionFromRemoveCollectionLink(Article article) {
		return onActionFromRemoveCollection(article);
	}

}
