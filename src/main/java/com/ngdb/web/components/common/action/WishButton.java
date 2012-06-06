package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Article;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class WishButton {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private boolean showText;

	@Inject
	private CurrentUser currentUser;

	@Property
	@Parameter
	private String returnPage;

	@CommitAfter
	Object onActionFromWish(Article article) {
		currentUser.wish(article);
		return returnPage;
	}

}
