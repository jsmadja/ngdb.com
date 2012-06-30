package com.ngdb.web.pages.article.game;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;

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
	private String value;

	@Property
	private Note note;

	@Inject
	private CurrentUser currentUser;

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

	public String getByNgh() {
		return Filter.byNgh.name();
	}

	public String getByOrigin() {
		return Filter.byOrigin.name();
	}

	public String getByPlatform() {
		return Filter.byPlatform.name();
	}

	public String getByPublisher() {
		return Filter.byPublisher.name();
	}

	public String getByReleaseDate() {
		return Filter.byReleaseDate.name();
	}

	public String getNgh() {
		return StringUtils.isNumeric(game.getNgh()) ? "NGH " + game.getNgh() : game.getNgh();
	}

	public User getUser() {
		return currentUser.getUser();
	}

}
