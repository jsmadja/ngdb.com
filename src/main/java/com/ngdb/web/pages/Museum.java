package com.ngdb.web.pages;

import static com.google.common.collect.Collections2.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.Predicates;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;

public class Museum {

	@Property
	private Article article;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private ReferenceService referenceService;

	// ---- Parameters
	@Persist
	@Property
	private Filter filter;

	@Persist
	@Property
	private String filterValue;

	@Persist
	@Property
	private Long id;

	@Persist
	@Property
	private User user;

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

	// ---- Publisher
	@Persist
	private Long filterPublisher;

	@Property
	private Publisher publisher;

	@Persist
	@Property
	private boolean filteredByGames;

	private static final Logger LOG = LoggerFactory.getLogger(Museum.class);

	void onActivate() {
		if (filterOrigin == null && filterPlatform == null) {
			init();
			this.user = null;
		}
	}

	boolean onActivate(User user) {
		init();
		this.user = user;
		return true;
	}

	boolean onActivate(String filter, String value) {
		init();
		this.filter = Filter.valueOf(Filter.class, filter);
		this.filterValue = value;
		if (StringUtils.isNumeric(value)) {
			this.id = Long.valueOf(filterValue);
			switch (this.filter) {
			case byOrigin:
				filterOrigin = id;
				break;
			case byPlatform:
				filterPlatform = id;
				break;
			case byPublisher:
				filterPublisher = id;
				break;
			}
		}
		return true;
	}

	private void init() {
		this.filterOrigin = referenceService.findOriginByTitle("Japan").getId();
		this.filterPlatform = referenceService.findPlatformByName("Neo·Geo CD").getId();
		this.filterPublisher = null;
		this.filteredByGames = true;
		this.filterValue = null;
		this.filter = Filter.none;
	}

	public List<Article> getArticles() {
		List<Predicate<Article>> filters = Lists.newArrayList();
		applyExternalParameters(filters);
		Collection<Article> filteredArticles = applyFilters(filters);
		return new ArrayList<Article>(filteredArticles);
	}

	private Collection<Article> applyFilters(List<Predicate<Article>> filters) {
		Collection<Article> filteredArticles;
		if (filteredByGames) {
			filteredArticles = new ArrayList<Article>(gameFactory.findAll());
		} else {
			filteredArticles = new ArrayList<Article>(hardwareFactory.findAll());
		}
		if (filterOrigin != null) {
			filters.add(new Predicates.OriginPredicate(currentOrigin()));
		}
		if (filterPlatform != null) {
			filters.add(new Predicates.PlatformPredicate(currentPlatform()));
		}
		if (filterPublisher != null) {
			filters.add(new Predicates.PublisherPredicate(currentPublisher()));
		}
		for (Predicate<Article> filter : filters) {
			filteredArticles = filter(filteredArticles, filter);
		}
		if (user != null) {
			filteredArticles = filter(filteredArticles, keepOnlyOwnedArticlesBy(user));
		}
		return filteredArticles;
	}

	private void applyExternalParameters(List<Predicate<Article>> filters) {
		switch (filter) {
		case byNgh:
			filters.add(new Predicate<Article>() {
				@Override
				public boolean apply(Article input) {
					if (input.getType().equals(Game.class)) {
						Game game = (Game) input;
						String ngh = game.getNgh();
						if (ngh != null) {
							return ngh.equalsIgnoreCase(filterValue);
						}
					}
					return false;
				}
			});
			break;
		case byOrigin:
			filterOrigin = id;
			break;
		case byPlatform:
			filterPlatform = id;
			break;
		case byPublisher:
			filterPublisher = id;
			break;
		case byReleaseDate:
			final Date releaseDate = toReleaseDate();
			filters.add(new Predicate<Article>() {
				@Override
				public boolean apply(Article input) {
					Date inputReleaseDate = input.getReleaseDate();
					return inputReleaseDate != null && inputReleaseDate.equals(releaseDate);
				}
			});
			break;
		case byTag:
			final Tag tag = referenceService.findTagById(id);
			this.filterValue = tag.getName();
			filters.add(new Predicate<Article>() {
				@Override
				public boolean apply(Article input) {
					return input.containsTag(tag);
				}
			});
			break;
		}
	}

	private Predicate<Article> keepOnlyOwnedArticlesBy(final User user) {
		return new Predicate<Article>() {
			@Override
			public boolean apply(Article input) {
				return user.owns(input);
			}
		};
	}

	Object onActionFromClearFilters() {
		init();
		return this;
	}

	Object onActionFromSelectGames() {
		this.filteredByGames = true;
		return this;
	}

	Object onActionFromSelectHardwares() {
		this.filteredByGames = false;
		return this;
	}

	Object onActionFromFilterPublisher(Publisher publisher) {
		filterPublisher = publisher.getId();
		return this;
	}

	public boolean isFilteredByThisPublisher() {
		return publisher.getId().equals(filterPublisher);
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

	public long getNumGames() {
		return gameFactory.getNumGames();
	}

	public long getNumHardwares() {
		return hardwareFactory.getNumHardwares();
	}

	public int getNumArticlesInThisPlatform() {
		return getArticlesFilterdByPlatform().size();
	}

	private Collection<Article> getArticlesFilterdByPlatform() {
		Predicates.PlatformPredicate filter = new Predicates.PlatformPredicate(platform);
		Collection<? extends Article> articles = (Collection<? extends Article>) getChosenArticlesByType();
		return new ArrayList<Article>(filter(articles, filter));
	}

	private Collection<? extends Article> getChosenArticlesByType() {
		Collection<? extends Article> articles;
		if (filteredByGames) {
			articles = gameFactory.findAll();
		} else {
			articles = hardwareFactory.findAll();
		}
		return articles;
	}

	public int getNumArticlesInThisOrigin() {
		Platform currentPlatform = referenceService.findPlatformById(filterPlatform);
		Predicates.PlatformPredicate filterByPlatform = new Predicates.PlatformPredicate(currentPlatform);
		Predicates.OriginPredicate filterByOrigin = new Predicates.OriginPredicate(origin);

		Collection<? extends Article> articles = getChosenArticlesByType();
		articles = filter(articles, filterByPlatform);
		articles = filter(articles, filterByOrigin);
		return articles.size();
	}

	public int getNumArticlesInThisPublisher() {
		Platform currentPlatform = referenceService.findPlatformById(filterPlatform);
		Origin currentOrigin = referenceService.findOriginById(filterOrigin);
		Predicates.PlatformPredicate filterByPlatform = new Predicates.PlatformPredicate(currentPlatform);
		Predicates.OriginPredicate filterByOrigin = new Predicates.OriginPredicate(currentOrigin);
		Predicates.PublisherPredicate filterByPublisher = new Predicates.PublisherPredicate(publisher);

		Collection<? extends Article> articles = getChosenArticlesByType();
		articles = filter(articles, filterByPlatform);
		articles = filter(articles, filterByOrigin);
		articles = filter(articles, filterByPublisher);
		return articles.size();
	}

	private Origin currentOrigin() {
		return referenceService.findOriginById(filterOrigin);
	}

	private Platform currentPlatform() {
		return referenceService.findPlatformById(filterPlatform);
	}

	public String getQueryLabel() {
		String queryLabel = "all ";
		if (filteredByGames) {
			queryLabel += "games";
		} else {
			queryLabel += "hardwares";
		}
		if (filterOrigin != null) {
			Origin origin = referenceService.findOriginById(filterOrigin);
			queryLabel += " from " + origin.getTitle();
		}
		if (filterPublisher != null) {
			Publisher publisher = currentPublisher();
			queryLabel += " published by " + publisher.getName();
		}
		if (filterPlatform != null) {
			Platform platform = referenceService.findPlatformById(filterPlatform);
			queryLabel += " on " + platform.getName();
		}
		if (filter != null) {
			switch (filter) {
			case byNgh:
				queryLabel += " with ngh '" + filterValue + "'";
				break;
			case byReleaseDate:
				queryLabel += " with release date '" + new SimpleDateFormat("MM/dd/yyyy").format(toReleaseDate()) + "'";
				break;
			case byTag:
				queryLabel += " with tag '" + filterValue + "'";
				break;
			}
		}
		return queryLabel;
	}

	private Publisher currentPublisher() {
		return referenceService.findPublisherBy(filterPublisher);
	}

	public int getNumResults() {
		return getArticles().size();
	}

	public boolean isArticleInThisPlatform() {
		return getNumArticlesInThisPlatform() > 0;
	}

	public boolean isArticleInThisOrigin() {
		return getNumArticlesInThisOrigin() > 0;
	}

	public boolean isArticleInThisPublisher() {
		return getNumArticlesInThisPublisher() > 0;
	}

	private Date toReleaseDate() {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(filterValue);
		} catch (ParseException e) {
			LOG.error("Wrong date: " + filterValue, e);
			return new Date();
		}
	}

}
