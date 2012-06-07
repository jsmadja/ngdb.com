package com.ngdb.web.pages.article.game;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.user.User;
import com.ngdb.web.pages.article.ArticleView;

public class GameView extends ArticleView {

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
	private String value;

	@Property
	private Note note;

	@Inject
	private Session session;

	@Property
	private User user;

	public void onActivate(Game game) {
		this.game = game;
		this.user = userSession.getUser();
	}

	public Game onPassivate() {
		return game;
	}

	@Override
	protected Game getArticle() {
		return game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
