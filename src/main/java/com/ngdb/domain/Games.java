package com.ngdb.domain;

import java.util.ArrayList;
import java.util.List;

import com.ngdb.persistence.ElementNonTrouveException;

public class Games {

	private static List<Game> games = new ArrayList<Game>();

	static {

		// -------------- AES

		Game game = new Game();
		game.setTitle("Street Hoop");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/09");
		game.setPlatform(Platform.AES);
		game.setOrigin(Origin.America);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.setBox(Box.HARD);
		game.addTag(Tag.MultiPlayers);
		game.addPicture(new Picture("http://img5.cherchons.com/4181586276/street-hoop-neogeo-aes-us-2948740320.jpg", 150, 150));
		game.addOwner(Users.findAll().get(0));
		game.setDetails("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed elementum porttitor turpis mattis tempus. Aliquam sed diam odio, sit amet lacinia ligula. Nunc accumsan mauris vel tortor posuere id pulvinar elit volutpat. Vestibulum in massa quis tortor ultrices gravida. Ut commodo sapien eu enim pulvinar vitae iaculis dolor rhoncus. Mauris elementum iaculis mauris, a dictum orci tincidunt eu. Sed dignissim nunc aliquet risus porta venenatis. Maecenas eu quam ut arcu fermentum interdum ut vel velit. Ut posuere semper lacus, sed iaculis mauris auctor sed. Nam hendrerit egestas magna non tempor. Cras convallis pellentesque purus, sed porttitor lorem sodales at. Duis laoreet, urna vitae imperdiet molestie, purus lorem commodo metus, eu sodales odio dui in lectus. Nullam dolor ipsum, consectetur nec suscipit sed, vulputate nec nibh. Quisque varius nibh at leo rhoncus et luctus dui elementum.");
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
		game.addPicture(new Picture("http://t0.gstatic.com/images?q=tbn:ANd9GcSR8WFM1Ib0t7ZQofv14_7-Qs1i-hIYIeyN9yYW5IkJ3GS6HiQMMQsk_Xqa", 200, 251));
		game.addProperty(new MarkProperty("neogeospirit", "http://neogeospirit.pagesperso-orange.fr/tests/streethoop_test.htm", "3/5"));
		games.add(game);

		// ----------- MVS

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
		game.addPicture(new Picture("http://img571.imageshack.us/img571/6133/mvsstreethoop.png", 239, 83));
		game.addWisher(Users.findAll().get(0));
		games.add(game);

		game = new Game();
		game.setTitle("Street Slam");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/08");
		game.setPlatform(Platform.MVS);
		game.setOrigin(Origin.America);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addTag(Tag.MultiPlayers);
		game.addPicture(new Picture("http://img109.imageshack.us/img109/5889/mvsstreetslam.png", 235, 91));
		games.add(game);

		game = new Game();
		game.setTitle("Dunk Dream");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1994/12/08");
		game.setPlatform(Platform.MVS);
		game.setOrigin(Origin.Japan);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addTag(Tag.MultiPlayers);
		game.addPicture(new Picture("http://img256.imageshack.us/img256/1996/mvsdunkdream.png", 218, 67));
		games.add(game);

		// ------------------ CD

		game = new Game();
		game.setTitle("Dunk Dream");
		game.setNgh("079");
		game.setPublisher(Publisher.dataEast);
		game.setReleaseDate("1995/01/20");
		game.setPlatform(Platform.CD);
		game.setOrigin(Origin.Japan);
		game.setGenre("Sports");
		game.setMegaCount(94L);
		game.addPicture(new Picture("http://img843.imageshack.us/img843/7169/cdjpdunkdream.jpg", 600, 525));
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
		game.addProperty(new Property("NGCD", "DECD-004E"));
		game.addTag(Tag.MultiPlayers);
		game.addPicture(new Picture("http://img854.imageshack.us/img854/7194/cdusstreethoop.jpg", 600, 518));
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
