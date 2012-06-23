package com.ngdb.web.pages.article.game;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Genre;

public class GameView {

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

	public void onActivate(Game game) {
		this.game = game;
	}

	public Game onPassivate() {
		return game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getDetails() {
		return StringEscapeUtils.unescapeHtml(game.getDetails());
	}

}
