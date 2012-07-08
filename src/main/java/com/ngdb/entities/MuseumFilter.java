package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicate;
import com.ngdb.Predicates;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.user.User;

public class MuseumFilter {

	private boolean filteredByGames;

	private Publisher filteredPublisher;

	private Origin filteredOrigin;

	private Platform filteredPlatform;

	private User filteredUser;

	private GameFactory gameFactory;

	private HardwareFactory hardwareFactory;

	private Tag filteredTag;

	private String filteredNgh;

	private Date filteredReleaseDate;

	private Map<String, Predicate<Article>> filters = new HashMap<String, Predicate<Article>>();

	private Collection<Article> chosenArticlesByType;

	public MuseumFilter(GameFactory gameFactory, HardwareFactory hardwareFactory) {
		this.gameFactory = gameFactory;
		this.hardwareFactory = hardwareFactory;
		clear();
	}

	public void clear() {
		this.filteredOrigin = null;
		this.filteredPlatform = null;
		this.filteredPublisher = null;
		this.filteredNgh = null;
		this.filteredReleaseDate = null;
		this.filteredTag = null;
		this.filteredByGames = true;
		this.filteredUser = null;
	}

	private Collection<Article> getChosenArticlesByType() {
		if (chosenArticlesByType == null) {
			if (filteredByGames) {
				if (filteredUser == null) {
					this.chosenArticlesByType = new ArrayList<Article>(gameFactory.findAll());
				} else {
					this.chosenArticlesByType = gameFactory.findAllOwnedBy(filteredUser);
				}
			} else {
				if (filteredUser == null) {
					this.chosenArticlesByType = new ArrayList<Article>(hardwareFactory.findAll());
				} else {
					this.chosenArticlesByType = hardwareFactory.findAllOwnedBy(filteredUser);
				}
			}
		}
		return new ArrayList<Article>(chosenArticlesByType);
	}

	public Collection<Article> getArticles() {
		Collection<Article> filteredArticles = getChosenArticlesByType();
		for (Predicate<Article> filter : filters.values()) {
			filteredArticles = filter(filteredArticles, filter);
		}
		return filteredArticles;
	}

	public boolean isFilteredBy(Publisher publisher) {
		if (filteredPublisher == null) {
			return false;
		}
		return publisher.getId().equals(filteredPublisher.getId());
	}

	public boolean isFilteredBy(Platform platform) {
		if (filteredPlatform == null) {
			return false;
		}
		return platform.getId().equals(filteredPlatform.getId());
	}

	public boolean isFilteredByGames() {
		return filteredByGames;
	}

	public boolean isFilteredBy(Origin origin) {
		if (filteredOrigin == null) {
			return false;
		}
		return origin.getId().equals(filteredOrigin.getId());
	}

	private Collection<Article> filterByPlatform(Collection<Article> articles) {
		if (filteredPlatform != null) {
			Predicates.PlatformPredicate filterByPlatform = new Predicates.PlatformPredicate(filteredPlatform);
			articles = filter(articles, filterByPlatform);
		}
		return articles;
	}

	private Collection<Article> filterByOrigin(Collection<Article> articles) {
		if (filteredOrigin != null) {
			Predicates.OriginPredicate filterByOrigin = new Predicates.OriginPredicate(filteredOrigin);
			articles = filter(articles, filterByOrigin);
		}
		return articles;
	}

	public void filterByHardwares() {
		this.chosenArticlesByType = null;
		this.filteredByGames = false;
	}

	public void filterByGames() {
		this.chosenArticlesByType = null;
		this.filteredByGames = true;
	}

	public void filterByUser(User user) {
		this.filteredUser = user;
	}

	public void filterByPublisher(Publisher publisher) {
		this.filteredPublisher = publisher;
		filters.put("publisher", new Predicates.PublisherPredicate(filteredPublisher));
	}

	public void filterByTag(Tag tag) {
		this.filteredTag = tag;
		filters.put("tag", new Predicates.TagPredicate(filteredTag));
	}

	public void filterByPlatform(Platform platform) {
		this.filteredPlatform = platform;
		filters.put("platform", new Predicates.PlatformPredicate(filteredPlatform));
	}

	public void filterByOrigin(Origin origin) {
		this.filteredOrigin = origin;
		filters.put("origin", new Predicates.OriginPredicate(filteredOrigin));
	}

	public void filterByNgh(String ngh) {
		this.filteredNgh = ngh;
		filters.put("ngh", new Predicates.NghPredicate(filteredNgh));
	}

	public void filterByReleaseDate(Date releaseDate) {
		this.filteredReleaseDate = releaseDate;
		filters.put("releaseDate", new Predicates.ReleaseDatePredicate(filteredReleaseDate));
	}

	public User getFilteredUser() {
		return filteredUser;
	}

	public Tag getFilteredTag() {
		return filteredTag;
	}

	public Date getFilteredReleaseDate() {
		return filteredReleaseDate;
	}

	public long getNumHardwares() {
		if (filteredUser != null) {
			return filteredUser.getHardwaresInCollection().size();
		}
		return hardwareFactory.getNumHardwares();
	}

	public int getNumArticlesInThisPlatform(Platform platform) {
		Collection<Article> articles = getChosenArticlesByType();
		articles = filter(articles, new Predicates.PlatformPredicate(platform));
		return articles.size();
	}

	public int getNumArticlesInThisOrigin(Origin origin) {
		Collection<Article> articles = getChosenArticlesByType();
		articles = filter(articles, new Predicates.OriginPredicate(origin));
		articles = filterByPlatform(articles);
		return articles.size();
	}

	public int getNumArticlesInThisPublisher(Publisher publisher) {
		Collection<Article> articles = getChosenArticlesByType();
		articles = filterByOrigin(articles);
		articles = filterByPlatform(articles);
		Predicates.PublisherPredicate filterByPublisher = new Predicates.PublisherPredicate(publisher);
		articles = filter(articles, filterByPublisher);
		return articles.size();
	}

	public long getNumGames() {
		if (filteredUser != null) {
			return filteredUser.getGamesInCollection().size();
		}
		return gameFactory.getNumGames();
	}

	public String getQueryLabel() {
		String queryLabel = "all ";
		if (filteredByGames) {
			queryLabel += orange("games");
		} else {
			queryLabel += orange("hardwares");
		}
		if (filteredOrigin != null) {
			queryLabel += " from " + orange(filteredOrigin.getTitle());
		}
		if (filteredPublisher != null) {
			queryLabel += " published by " + orange(filteredPublisher.getName());
		}
		if (filteredPlatform != null) {
			queryLabel += " on " + orange(filteredPlatform.getName());
		}
		if (filteredNgh != null) {
			queryLabel += " with ngh " + orange(filteredNgh);
		}
		if (filteredReleaseDate != null) {
			queryLabel += " released at " + orange(new SimpleDateFormat("MM/dd/yyyy").format(filteredReleaseDate));
		}
		if (filteredTag != null) {
			queryLabel += " with tag " + orange(filteredTag.getName());
		}
		if (filteredUser != null) {
			queryLabel += " owned by " + orange(filteredUser.getLogin());
		}
		return queryLabel;
	}

	private String orange(String name) {
		return "<span class=\"orange\">" + name + "</span>";
	}

}
