package com.ngdb.web.pages;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.Game;
import com.ngdb.entities.Genre;
import com.ngdb.entities.Origin;
import com.ngdb.entities.Platform;
import com.ngdb.entities.Publisher;

public class Games {

	@Property
	private Game game;

	@Property
	private Collection<Game> games;

	@Inject
	private Session session;

	private enum Filter {
		none, byReleaseDate, byNgh, byPlatform, byOrigin, byGenre, byPublisher
	};

	private Filter filter = Filter.none;

	private String filterValue;

	private Long id;

	private Predicate<Game> additionnalFilter;

	void onActivate(String filter, String value) {
		if (StringUtils.isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
			if (StringUtils.isNumeric(filterValue)) {
				id = Long.valueOf(filterValue);
			}
		}
	}

	@SetupRender
	void init() {
		Criteria criteria = createCriteria();
		this.games = criteria.list();
		if (additionnalFilter != null) {
			this.games = Collections2.filter(this.games, additionnalFilter);
		}
	}

	private Criteria createCriteria() {
		Criteria criteria = session.createCriteria(Game.class).addOrder(asc("title"));
		switch (filter) {
		case byReleaseDate:
			int year = Integer.valueOf(filterValue.split("-")[0]);
			Date searchDate = new DateTime().withTimeAtStartOfDay().withYear(year).toDate();
			criteria = criteria.add(between("releaseDate", searchDate, searchDate));
			break;
		case byNgh:
			criteria = criteria.add(eq("ngh", filterValue));
			break;
		case byPlatform:
			Platform platform = (Platform) session.load(Platform.class, id);
			criteria = criteria.add(eq("platform", platform));
			break;
		case byOrigin:
			Origin origin = (Origin) session.load(Origin.class, id);
			criteria = criteria.add(eq("origin", origin));
			break;
		case byGenre:
			final Genre genre = (Genre) session.load(Genre.class, id);
			additionnalFilter = new Predicate<Game>() {
				@Override
				public boolean apply(Game game) {
					return game.getGenres().contains(genre);
				}
			};
			break;
		case byPublisher:
			Publisher publisher = (Publisher) session.load(Publisher.class, id);
			criteria = criteria.add(eq("publisher", publisher));
			break;
		}
		return criteria;
	}

}
