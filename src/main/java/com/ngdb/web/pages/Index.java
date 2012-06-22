package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Game;

public class Index {

	@Inject
	private GameFactory gameFactory;

	@Inject
	private WishBox wishBox;

	@Property
	private Game randomGame1;

	@Property
	private Game randomGame2;

	@Property
	private Game randomGame3;

	@SetupRender
	public void init() {
		randomGame1 = gameFactory.getRandomGame();
		randomGame2 = gameFactory.getRandomGame();
		randomGame3 = gameFactory.getRandomGame();
	}

	public Long getNumGames() {
		return gameFactory.getNumGames();
	}

	public Long getNumWhishes() {
		return wishBox.getNumWishes();
	}

}
