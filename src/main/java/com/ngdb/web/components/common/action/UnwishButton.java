package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class UnwishButton {

	@Property
	@Parameter
	private Article article;

	@Inject
	private CurrentUser currentUser;

	@Property
	@Parameter
	private String returnPage;

	@Property
	@Parameter
	private boolean asButton;

	@CommitAfter
	Object onActionFromUnwish(Article article) {
		currentUser.unwish(article);
		return returnPage;
	}

	@CommitAfter
	Object onActionFromUnwishLink(Article article) {
		return onActionFromUnwish(article);
	}

}
