package com.ngdb.web.pages;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.annotations.SetupRender;

import com.ngdb.domain.Game;
import com.ngdb.domain.Games;

public class Index {

	private List<Game> games;
	private Game mostOwnedGame;
	private Game mostWantedGame;
	private Game lastForSaleGame;

	@SetupRender
	public void setUpRender() {
		games = Games.findAll();
		mostOwnedGame = randomGame();
		mostWantedGame = randomGame();
		lastForSaleGame = randomGame();
	}

	public int getNumGames() {
		return Games.findAll().size();
	}

	public int getNumWhishes() {
		int numWishes = 0;
		List<Game> games = Games.findAll();
		for (Game game : games) {
			numWishes += game.getWishers().size();
		}
		return numWishes;
	}

	public Game getMostOwnedGame() {
		return mostOwnedGame;
	}

	public Game getMostWantedGame() {
		return mostWantedGame;
	}

	public Game getLastForSaleGame() {
		return lastForSaleGame;
	}

	private Game randomGame() {
		int nextInt = RandomUtils.nextInt(games.size());
		return games.get(nextInt);
	}

}
