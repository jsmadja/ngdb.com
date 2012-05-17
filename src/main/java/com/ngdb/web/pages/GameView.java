package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Comment;
import com.ngdb.entities.Game;
import com.ngdb.entities.Genre;
import com.ngdb.entities.Note;
import com.ngdb.entities.Review;
import com.ngdb.entities.Tag;

public class GameView {

	@Property
	@Persist("entity")
	private Game game;

	@Property
	private Note property;

	@Property
	private Tag tag;

	@Property
	private Review review;

	@Property
	private Genre genre;

	@Property
	private Comment comment;

	@Property
	private String value;

	@Property
	private Note note;

	@Inject
	private Session session;

	public void onActivate(Game game) {
		this.game = game;
	}

	public Game onPassivate() {
		return game;
	}

	public int getNumAvailableCopy() {
		return game.getAvailableCopyCount();
	}

	public int getSealedQuantity() {
		return 3;
	}

	public String getSealedAverage() {
		return "$3.00";
	}

	public String getSealedMax() {
		return "$30.00";
	}

	public int getMintQuantity() {
		return 3;
	}

	public String getMintAverage() {
		return "$3.00";
	}

	public String getMintMax() {
		return "$30.00";
	}

	public int getUsedQuantity() {
		return 3;
	}

	public String getUsedAverage() {
		return "$3.00";
	}

	public String getUsedMax() {
		return "$30.00";
	}

	public String getPlatform() {
		return "platform";
	}

	public String getNgh() {
		return "ngh";
	}

	public String getByGenre() {
		return "genre";
	}

	public String getPublisher() {
		return "publisher";
	}

	public String getOrigin() {
		return "origin";
	}

	public String getReleaseDate() {
		return "releaseDate";
	}
}
