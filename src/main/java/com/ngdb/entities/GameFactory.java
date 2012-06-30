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
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
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

	public List<Game> findAllByPublisher(Publisher publisher) {
		return allGames().add(eq("publisher", publisher)).list();
	}

	public Long getNumGames() {
		return (Long) session.createCriteria(Game.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
	}

	public List<Game> findAll() {
		return allGames().list();
	}

	public List<Game> findAllWithMainPicture() {
		return session.createQuery("SELECT g FROM Game g WHERE g.pictures.pictures is not empty").list();
	}

	private Criteria allGames() {
		return session.createCriteria(Game.class).setCacheable(true).addOrder(asc("title"));
	}

	public Collection<Game> findAllByReleasedThisMonth() {
		List<Game> games = findAll();
		return filter(games, releasedThisMonth);
	}

	public Game getRandomGameWithMainPicture() {
		List<Game> gamesWithMainPicture = findAllWithMainPicture();
		return gamesWithMainPicture.get(RandomUtils.nextInt(gamesWithMainPicture.size()));
	}

	public Collection<Article> findAllByOriginAndPlatform(Origin origin, Platform platform) {
		return allGames().add(eq("origin", origin)).add(eq("platform", platform)).list();
	}

	public List<Game> findAllByTag(final Tag tag) {
		Criteria criteria = allGames();
		Predicate<Game> additionnalFilter = new Predicate<Game>() {
			@Override
			public boolean apply(Game game) {
				return game.containsTag(tag);
			}
		};
		return new ArrayList<Game>(filter(criteria.list(), additionnalFilter));
	}
}
