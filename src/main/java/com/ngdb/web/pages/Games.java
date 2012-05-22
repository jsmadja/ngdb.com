package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Game;
import com.ngdb.web.Filter;
import com.ngdb.web.services.domain.GameService;

public class Games {

	@Property
	private Game game;

	@Property
	private Collection<Game> games;

	@Inject
	private GameService gameService;

	private Filter filter = Filter.none;

	private String filterValue;

	void onActivate(String filter, String value) {
		if (StringUtils.isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
		}
	}

	@SetupRender
	void init() {
		this.games = gameService.findAll(filter, filterValue);
	}

}
