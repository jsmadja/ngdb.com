package com.ngdb.web.pages;

import static com.google.common.collect.Collections2.filter;
import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.ngdb.Comparators;
import com.ngdb.base.EvenOdd;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Games {

	@Persist
	@Property
	private Game game;

	@Persist
	@Property
	private List<Game> games;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private ReferenceService referenceService;

	@Inject
	private CurrentUser currentUser;

	private Filter filter = Filter.none;

	private String filterValue;

	private Long id;

	private EvenOdd evenOdd = new EvenOdd();

	private List<Predicate> filters = new ArrayList<Predicate>();

	void onActivate(String filter, String value) {
		if (isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
			if (StringUtils.isNumeric(filterValue)) {
				this.id = Long.valueOf(filterValue);
			}
		}
	}

	/*
	 * Object onActionFromAes() { filters.add(keepAesOnly); return this; }
	 * 
	 * public boolean isFilteredByAes() { return filters.contains(keepAesOnly); }
	 * 
	 * public int getNumGamesOfAES() { return filter(this.games, keepAesOnly).size(); }
	 * 
	 * Object onActionFromMvs() { filters.add(keepMvsOnly); return this; }
	 * 
	 * public boolean isFilteredByMvs() { return filters.contains(keepMvsOnly); }
	 * 
	 * public int getNumGamesOfMVS() { return filter(this.games, keepMvsOnly).size(); }
	 * 
	 * Object onActionFromCd() { filters.add(keepCdOnly); return this; }
	 * 
	 * public boolean isFilteredByCd() { return filters.contains(keepMvsOnly); }
	 * 
	 * public int getNumGamesOfCD() { return filter(this.games, keepMvsOnly).size(); }
	 * 
	 * Object onActionFromJapan() { filters.add(keepJapanOnly); return this; }
	 * 
	 * public boolean isFilteredByJapan() { return filters.contains(keepJapanOnly); }
	 * 
	 * public int getNumGamesOfJapan() { return filter(this.games, keepJapanOnly).size(); }
	 * 
	 * Object onActionFromUsa() { filters.add(keepUsaOnly); return this; }
	 * 
	 * public boolean isFilteredByUSA() { return filters.contains(keepUsaOnly); }
	 * 
	 * public int getNumGamesOfUSA() { return filter(this.games, keepUsaOnly).size(); }
	 */

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
		Collection<Game> filteredGames = new ArrayList<Game>(games);
		for (Predicate<Game> filter : filters) {
			filteredGames = filter(filteredGames, filter);
		}
		this.games = new ArrayList<Game>(filteredGames);
		Collections.sort(games, Comparators.gamesByTitlePlatformOrigin);
	}

	public String getRowClass() {
		return evenOdd.next();
	}

	public User getUser() {
		return currentUser.getUser();
	}

}
