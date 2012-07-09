package com.ngdb.web.pages;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.web.pages.base.Redirections;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Result {

	@Persist
	private List<Article> results;

	@Property
	private Article result;

	@Persist
	private String search;

	@Inject
	private Registry registry;

	@Inject
	private CurrentUser currentUser;

	private static final Logger LOG = LoggerFactory.getLogger(Result.class);

	@SetupRender
	public void setup() {
		LOG.info(currentUser.getUsername() + " is searching for '" + search + "'");
		results = registry.findGamesMatching(search);
	}

	public void setResults(List<Article> results) {
		this.results = results;
	}

	public Collection<Article> getResults() {
		return results;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

	public String getViewPage() {
		return Redirections.toViewPage(result);
	}

}
