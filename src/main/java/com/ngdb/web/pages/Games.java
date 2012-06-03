package com.ngdb.web.pages;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.joda.time.DateTime;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.UserSession;

public class Games {

	@Property
	private Game game;

	@Property
	private Collection<Game> games;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private ReferenceService referenceService;

	@Inject
	private UserSession userSession;

	@Inject
	private Session session;

	private Filter filter = Filter.none;

	private String filterValue;

	private Long id;

	void onActivate(String filter, String value) {
		if (StringUtils.isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
			if (StringUtils.isNumeric(filterValue)) {
				this.id = Long.valueOf(filterValue);
			}
		}
	}

	@SetupRender
	void init() {
		switch (filter) {
		case byGenre:
			Genre genre = referenceService.findGenreById(id);
			this.games = gameFactory.findAllByGenre(genre);
			break;
		case byNgh:
			this.games = gameFactory.findAllByNgh(filterValue);
			break;
		case byOrigin:
			Origin origin = referenceService.findOriginById(id);
			this.games = gameFactory.findAllByOrigin(origin);
			break;
		case byPlatform:
			Platform platform = referenceService.findPlatformById(id);
			this.games = gameFactory.findAllByPlatform(platform);
			break;
		case byPublisher:
			Publisher publisher = referenceService.findPublisherBy(id);
			this.games = gameFactory.findAllByPublisher(publisher);
			break;
		case byReleaseDate:
			int year = Integer.valueOf(filterValue.split("-")[0]);
			Date releaseDate = new DateTime().withTimeAtStartOfDay().withYear(year).toDate();
			this.games = gameFactory.findAllByReleaseDate(releaseDate);
			break;
		default:
			this.games = gameFactory.findAll();
			break;
		}
	}

	public boolean isAddableToCollection() {
		return userSession.canAddToCollection(game);
	}

	@CommitAfter
	Object onActionFromCollection(Game game) {
		User user = userSession.getUser();
		CollectionObject collectionObject = new CollectionObject(user, game);
		session.persist(collectionObject);
		user.addInCollection(collectionObject);
		return this;
	}

}
