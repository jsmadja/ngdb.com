package com.ngdb.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.web.services.domain.GameService;
import com.ngdb.web.services.domain.WishService;

public class Index {

	@Inject
	private GameService gameService;

	@Inject
	private WishService wishService;

	public Long getNumGames() {
		return gameService.getNumGames();
	}

	public Long getNumWhishes() {
		return wishService.getNumWishes();
	}

}
