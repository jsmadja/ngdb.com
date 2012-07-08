package com.ngdb.web.pages;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Comparators.byTitlePlatformOrigin;
import static java.util.Collections.sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.Predicates;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Article;
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
	private Article game;

	@Persist
	@Property
	private List<Article> games;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private ReferenceService referenceService;

	@Inject
	private CurrentUser currentUser;

	@Persist
	@Property
	private Filter filter;

	@Persist
	@Property
	private String filterValue;

	@Persist
	@Property
	private Long id;

	// ---- Platform
	@Persist
	private Long filterPlatform;

	@Property
	private Platform platform;

	// ---- Origin

	@Persist
	private Long filterOrigin;

	@Property
	private Origin origin;

	private static final Logger LOG = LoggerFactory.getLogger(Games.class);

	void onActivate() {
		this.games = new ArrayList<Article>(gameFactory.findAll());
	}

	boolean onActivate(String filter, String value) {
		this.filterOrigin = null;
		this.filterPlatform = null;
		this.games = new ArrayList<Article>(gameFactory.findAll());
		this.filter = Filter.valueOf(Filter.class, filter);
		this.filterValue = value;
		if (StringUtils.isNumeric(value)) {
			this.id = Long.valueOf(filterValue);
		}
		// initFilters();
		return true;
	}

	// ---- Platform

	public List<Platform> getPlatforms() {
		return referenceService.getPlatforms();
	}

	Object onActionFromFilterPlatform(Platform platform) {
		filterPlatform = platform.getId();
		return this;
	}

	public boolean isFilteredByThisPlatform() {
		return platform.getId().equals(filterPlatform);
	}

	// ---- Origin

	public List<Origin> getOrigins() {
		return referenceService.getOrigins();
	}

	Object onActionFromFilterOrigin(Origin origin) {
		filterOrigin = origin.getId();
		return this;
	}

	public boolean isFilteredByThisOrigin() {
		return origin.getId().equals(filterOrigin);
	}

	// ---- Publisher

	public List<Publisher> getPublishers() {
		return referenceService.getPublishers();
	}

	@SetupRender
	void init() {
		filterGames();
		sort(games, byTitlePlatformOrigin);
	}

	private void filterGames() {
		Collection<Article> filteredGames = new ArrayList<Article>(games);
		List<Predicate<Article>> filters = Lists.newArrayList();
		if (filterOrigin != null) {
			filters.add(new Predicates.OriginPredicate(referenceService.findOriginById(filterOrigin)));
		}
		if (filterPlatform != null) {
			filters.add(new Predicates.PlatformPredicate(referenceService.findPlatformById(filterPlatform)));
		}
		for (Predicate<Article> filter : filters) {
			filteredGames = filter(filteredGames, filter);
		}
		this.games = new ArrayList<Article>(filteredGames);
	}

	/*
	 * private void initFilters() { if (filter == null) { filter = Filter.none; } switch (filter) { case byNgh: this.games = new ArrayList<Article>(gameFactory.findAllByNgh(filterValue)); break; case byOrigin: filterOrigin = id; filterValue = null; break; case byPlatform: filterPlatform = id; filterValue = null; break; case byPublisher: filterPublisher = id; filterValue = null; break; case byReleaseDate: Date releaseDate = toReleaseDate(); this.games = new ArrayList<Article>(gameFactory.findAllByReleaseDate(releaseDate)); break; case byTag: Tag tag = referenceService.findTagById(id); this.games = new ArrayList<Article>(gameFactory.findAllByTag(tag)); this.filterValue = tag.getName(); break; default: this.games = new ArrayList<Article>(gameFactory.findAll()); break; } }
	 */

	private Date toReleaseDate() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(filterValue);
		} catch (ParseException e) {
			LOG.error("Wrong date: " + filterValue, e);
			return new Date();
		}
	}

	public User getUser() {
		return currentUser.getUser();
	}

	public int getNumResults() {
		return this.games.size();
	}

	Object onActionFromClearFilters() {
		onActivate(Filter.none.name(), null);
		return this;
	}
}
