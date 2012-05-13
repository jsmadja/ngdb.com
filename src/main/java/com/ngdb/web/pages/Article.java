package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Property;

import com.ngdb.domain.Game;
import com.ngdb.domain.Games;
import com.ngdb.domain.Picture;
import com.ngdb.domain.Tag;
import com.ngdb.persistence.ElementNonTrouveException;

public class Article {

	@Property
	private Game game;

	@Property
	private com.ngdb.domain.Property property;

	@Property
	private Tag tag;

	@Property
	private String value;

	public void onActivate(Long gameId) {
		try {
			game = Games.findById(gameId);
		} catch (ElementNonTrouveException e) {
			game = Game.EMPTY;
		}
	}

	public Picture getPicture() {
		if (game.getPictures().isEmpty()) {
			return Picture.EMPTY;
		}
		return game.getPictures().get(0);
	}

}
