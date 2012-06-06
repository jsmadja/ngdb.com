package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class AddToCollectionButton {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private boolean showText;

	@Property
	@Parameter
	private String returnPage;

	@Inject
	private CurrentUser currentUser;

	@CommitAfter
	Object onActionFromCollection(Article article) {
		currentUser.addToCollection(article);
		return returnPage;
	}

}
