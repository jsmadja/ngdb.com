package com.ngdb.web.pages;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Game;

public class Result {

	@Persist
	private List<Game> results;

	@Property
	private Game result;

	@Persist
	private String search;

	@Inject
	private Registry registry;

	@SetupRender
	public void setup() {
		results = registry.findGamesMatching(search);
	}

	public void setResults(List<Game> results) {
		this.results = results;
	}

	public Collection<Game> getResults() {
		return results;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

}
