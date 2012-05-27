package com.ngdb.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.WishBox;

public class Index {

	@Inject
	private GameFactory gameFactory;

	@Inject
	private WishBox wishBox;

	public Long getNumGames() {
		return gameFactory.getNumGames();
	}

	public Long getNumWhishes() {
		return wishBox.getNumWishes();
	}

}
