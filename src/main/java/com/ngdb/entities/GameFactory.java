package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Predicates.releasedThisMonth;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.rowCount;
import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;

@SuppressWarnings("unchecked")
public class GameFactory {

	@Inject
	private Session session;

	public List<Game> findAllByReleaseDate(Date releaseDate) {
		return allGames().add(between("releaseDate", releaseDate, releaseDate)).list();
	}

	public List<Game> findAllByNgh(String ngh) {
		return allGames().add(eq("ngh", ngh)).list();
	}

	public List<Game> findAllByPlatform(Platform platform) {
		return allGames().add(eq("platform", platform)).list();
	}

	public List<Game> findAllByOrigin(Origin origin) {
		return allGames().add(eq("origin", origin)).list();
	}

	public List<Game> findAllByGenre(final Genre genre) {
		Criteria criteria = allGames();
		Predicate<Game> additionnalFilter = new Predicate<Game>() {
			@Override
			public boolean apply(Game game) {
				return game.isOfGenre(genre);
			}
		};
		return new ArrayList<Game>(filter(criteria.list(), additionnalFilter));
	}

	public List<Game> findAllByPublisher(Publisher publisher) {
		return allGames().add(eq("publisher", publisher)).list();
	}

	public Long getNumGames() {
		return (Long) session.createCriteria(Game.class).setProjection(rowCount()).uniqueResult();
	}

	public List<Game> findAll() {
		return allGames().list();
	}

	private Criteria allGames() {
		return session.createCriteria(Game.class).setCacheable(true).addOrder(asc("title"));
	}

	public Collection<Game> findAllByReleasedThisMonth() {
		List<Game> games = findAll();
		return filter(games, releasedThisMonth);
	}

	public Game getRandomGame() {
		return findAll().get(RandomUtils.nextInt(getNumGames().intValue()));
	}
}
