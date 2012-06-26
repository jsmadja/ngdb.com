package com.ngdb.web.components.article;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class CommentBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	@Validate("required")
	private Comment comment;

	@Property
	private String commentText;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private GameFactory gameFactory;

	@CommitAfter
	public Object onSuccess() {
		if (isNotBlank(commentText)) {
			currentUser.addCommentOn(article, commentText);
		}
		return this;
	}

	public boolean getHasNoComments() {
		return getComments().isEmpty();
	}

	public User getUser() {
		return currentUser.getUser();
	}

	public Set<Comment> getComments() {
		if (article instanceof Game) {
			Game game = (Game) article;
			Set<Comment> comments = new TreeSet<Comment>(game.getComments().all());
			List<Game> relatedGames = gameFactory.findAllByNgh(game.getNgh());
			for (Game relatedGame : relatedGames) {
				comments.addAll(relatedGame.getComments().all());
			}
			return comments;
		}
		return article.getComments().all();
	}

}
