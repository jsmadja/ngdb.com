package com.ngdb.web.components.article;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class CommentBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	private Comment comment;

	@Property
	private String commentText;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private Session session;

	@CommitAfter
	public Object onSuccess() {
		User user = currentUser.getUser();
		session.merge(new Comment(commentText, user, article));
		return this;
	}

	public boolean getHasNoComments() {
		return article.getComments().isEmpty();
	}

	public User getUser() {
		return currentUser.getUser();
	}

}
