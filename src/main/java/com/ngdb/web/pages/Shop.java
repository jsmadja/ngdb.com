package com.ngdb.web.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import com.ngdb.domain.Game;
import com.ngdb.domain.Games;
import com.ngdb.domain.Picture;

public class Shop {

	@Property
	private Game game;

	@Property
	private List<Game> games;

	@SetupRender
	public void setupRender() {
		games = Games.findAll();
	}

	public Picture getPicture() {
		if (game.getPictures().isEmpty()) {
			return Picture.EMPTY;
		}
		return game.getPictures().get(0);
	}

}
