package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Tag;

public class Registry {

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private Session session;

	public List<Game> findGamesMatching(final String search) {
		List<Game> articles = new ArrayList<Game>();
		if (StringUtils.isNotBlank(search)) {
			final String searchItem = search.toLowerCase().trim();
			Collection<Game> games = gameFactory.findAll();
			Collection<Game> matchingGames = filter(games, new Predicate<Game>() {
				@Override
				public boolean apply(Game game) {
					return game.getTitle().toLowerCase().contains(searchItem);
				}
			});
			articles.addAll(matchingGames);
		}
		sort(articles, byTitlePlatformOrigin);
		return articles;
	}

	public List<Article> findAll() {
		List<Article> articles = new ArrayList<Article>();
		articles.addAll(gameFactory.findAll());
		articles.addAll(hardwareFactory.findAll());
		return articles;
	}

	public List<Game> findLastUpdates() {
		return session.createCriteria(Game.class).setCacheable(true).setMaxResults(7).addOrder(desc("modificationDate")).list();
	}

	public Collection<String> findAllTags() {
		return session.createCriteria(Tag.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
	}

	public Collection<String> findAllPropertyNames() {
		return session.createCriteria(Note.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
	}

}
