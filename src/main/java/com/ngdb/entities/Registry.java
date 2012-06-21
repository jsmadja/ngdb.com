package com.ngdb.entities;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;

public class Registry {

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	public Collection<Article> findArticlesMatching(final String search) {
		Collection<Article> articles = new ArrayList<Article>();
		final String searchItem = search.toLowerCase().trim();
		Collection<Game> games = gameFactory.findAll();
		Collection<Game> matchingGames = Collections2.filter(games, new Predicate<Game>() {
			@Override
			public boolean apply(Game game) {
				return game.getTitle().toLowerCase().contains(searchItem);
			}
		});
		articles.addAll(matchingGames);

		Collection<Hardware> hardwares = hardwareFactory.findAll();
		Collection<Hardware> matchingHardwares = Collections2.filter(hardwares, new Predicate<Hardware>() {
			@Override
			public boolean apply(Hardware hardware) {
				return hardware.getTitle().toLowerCase().contains(searchItem);
			}
		});
		articles.addAll(matchingHardwares);
		return articles;
	}

	public Collection<Article> findAll() {
		Collection<Article> articles = new ArrayList<Article>();
		articles.addAll(gameFactory.findAll());
		articles.addAll(hardwareFactory.findAll());
		return articles;
	}

}
