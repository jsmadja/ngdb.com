package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Article;
import com.ngdb.entities.CollectionObject;
import com.ngdb.entities.Comment;
import com.ngdb.entities.Game;
import com.ngdb.entities.Genre;
import com.ngdb.entities.Note;
import com.ngdb.entities.Review;
import com.ngdb.entities.Tag;
import com.ngdb.entities.User;
import com.ngdb.entities.Wish;

public class GameView extends ArticleView {

	@Property
	@Persist("entity")
	private Game game;

	@Property
	private Genre genre;

	@Property
	private Note property;

	@Property
	private Tag tag;

	@Property
	private Review review;

	@Property
	private Comment comment;

	@Property
	private String value;

	@Property
	private Note note;

	@Inject
	protected Session session;

	@Property
	private String commentText;

	public void onActivate(Game game) {
		this.game = game;
	}

	@CommitAfter
	Object onActionFromCollection(Game game) {
		User currentUser = userService.getCurrentUser();
		CollectionObject collection = new CollectionObject(currentUser, game);
		session.merge(collection);
		return GameView.class;
	}

	@CommitAfter
	Object onActionFromWishList(Game game) {
		User currentUser = userService.getCurrentUser();
		Wish wish = new Wish(currentUser, game);
		session.merge(wish);
		return GameView.class;
	}

	@CommitAfter
	public Object onSuccess() {
		User user = userService.getCurrentUser();
		Comment comment = new Comment(commentText, user, getArticle());
		session.merge(comment);
		return GameView.class;
	}

	public Game onPassivate() {
		return game;
	}

	public String getByPlatform() {
		return "byPlatform";
	}

	public String getByNgh() {
		return "byNgh";
	}

	public String getByGenre() {
		return "byGenre";
	}

	public String getByPublisher() {
		return "byPublisher";
	}

	@Override
	protected Article getArticle() {
		return game;
	}

}
