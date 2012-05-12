package com.ngdb.domain;

import java.util.ArrayList;
import java.util.List;

import com.ngdb.persistence.ElementNonTrouveException;

public class Games {

	private static List<Game> games = new ArrayList<Game>();

	static {
		Game game = new Game();
		game.setTitle("Street Hoop");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/09");
		game.setPlatform(Platform.AES);
		game.setOrigin(Origin.Europe);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addTag(Tag.MultiPlayers);
		games.add(game);

		game = new Game();
		game.setTitle("Street Hoop");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/08");
		game.setPlatform(Platform.MVS);
		game.setOrigin(Origin.Europe);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addTag(Tag.MultiPlayers);
		games.add(game);

		game = new Game();
		game.setTitle("Street Slam");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/09");
		game.setPlatform(Platform.AES);
		game.setOrigin(Origin.America);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addProperty(new Property("Note", "In this version of the game, players instead get to choose from US cities instead of Countries."));
		game.addTag(Tag.MultiPlayers);
		games.add(game);

		game = new Game();
		game.setTitle("Dunk Dream");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/09");
		game.setPlatform(Platform.AES);
		game.setOrigin(Origin.Japan);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addProperty(new Property("Japanese title", "ダンクドリーム"));
		game.addTag(Tag.MultiPlayers);
		game.addProperty(new MarkProperty("neogeospirit", "http://neogeospirit.pagesperso-orange.fr/tests/streethoop_test.htm", "3/5"));
		games.add(game);

		game = new Game();
		game.setTitle("Dunk Dream");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1995/01/20");
		game.setPlatform(Platform.CD);
		game.setOrigin(Origin.Japan);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addPicture(new Picture("http://neogeospirit.pagesperso-orange.fr/images/streethoop_jaquette.jpg", 210, 210));
		game.addProperty(new Property("Japanese title", "ダンクドリーム"));
		game.addProperty(new Property("NGCD", "DECD-004"));
		game.addTag(Tag.MultiPlayers);
		games.add(game);

		game = new Game();
		game.setTitle("Street Hoop");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1995/01/20");
		game.setPlatform(Platform.CD);
		game.setOrigin(Origin.America);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addProperty(new Property("Japanese title", "ダンクドリーム"));
		game.addProperty(new Property("NGCD", "DECD-004E"));
		game.addTag(Tag.MultiPlayers);
		games.add(game);
	}

	public static List<Game> findAll() {
		return games;
	}

	public static Game findById(Long gameId) throws ElementNonTrouveException {
		for (Game game : games) {
			if (game.getId().equals(gameId)) {
				return game;
			}
		}
		throw new ElementNonTrouveException("Cannot find game with id " + gameId);
	}
}
