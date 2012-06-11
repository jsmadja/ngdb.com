package com.ngdb.web.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;
import com.ngdb.web.Category;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Museum {

	@Property
	private Article game;

	@Property
	private Collection<? extends Article> games;

	@Property
	private Article hardware;

	@Property
	private Collection<? extends Article> hardwares;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private Population population;

	private Category category;

	private Long id;

	private User user;

	void onActivate(String category, String value) {
		if (isNotBlank(category)) {
			this.category = Category.valueOf(Category.class, category);
			if (StringUtils.isNumeric(value)) {
				id = Long.valueOf(value);
			}
		}
	}

	@SetupRender
	public void init() {
		if (id == null) {
			games = gameFactory.findAll();
			hardwares = hardwareFactory.findAll();
		} else {
			user = population.findById(id);
			games = user.getGamesInCollection();
			hardwares = user.getHardwaresInCollection();
		}
	}

}
