package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;

public class Registry {

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	public List<Game> findGamesMatching(final String search) {
		List<Game> articles = new ArrayList<Game>();
		final String searchItem = search.toLowerCase().trim();
		Collection<Game> games = gameFactory.findAll();
		Collection<Game> matchingGames = filter(games, new Predicate<Game>() {
			@Override
			public boolean apply(Game game) {
				return game.getTitle().toLowerCase().contains(searchItem);
			}
		});
		articles.addAll(matchingGames);
		return articles;
	}

	public List<Article> findAll() {
		List<Article> articles = new ArrayList<Article>();
		articles.addAll(gameFactory.findAll());
		articles.addAll(hardwareFactory.findAll());
		return articles;
	}

}
