package com.ngdb.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.persistence.ElementNonTrouveException;

public class Games {

	private static List<Article> articles = new ArrayList<Article>();

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
		game.addReview(new Review("neogeo-spirits", "http://google.fr", "3/5"));
		game.addComment(new Comment("Voici un commentaire", Users.findAll().get(3)));
		articles.add(game);

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
		articles.add(game);

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
		articles.add(game);

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
		articles.add(game);

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
		articles.add(game);

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
		articles.add(game);

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
		articles.add(game);
	}

	public static List<Article> findAll() {
		return articles;
	}

	public static Collection<Game> findAllGames() {
		return toGames(keepOnlyGames());
	}

	private static Collection<Article> keepOnlyGames() {
		Collection<Article> games = Collections2.filter(articles, new Predicate<Article>() {
			@Override
			public boolean apply(Article article) {
				return article instanceof Game;
			}
		});
		return games;
	}

	private static Collection<Game> toGames(Collection<Article> games) {
		Function<Article, Game> function = new Function<Article, Game>() {
			@Override
			public Game apply(Article article) {
				return (Game) article;
			}
		};
		Collection<Game> transform = Collections2.transform(games, function);
		return transform;
	}

	public static Article findById(Long articleId) throws ElementNonTrouveException {
		for (Article article : articles) {
			if (article.getId().equals(articleId)) {
				return article;
			}
		}
		throw new ElementNonTrouveException("Cannot find article with id " + articleId);
	}

	public static Game findGameById(Long gameId) {
		try {
			return (Game) findById(gameId);
		} catch (ElementNonTrouveException e) {
			return Game.EMPTY;
		}
	}
}
