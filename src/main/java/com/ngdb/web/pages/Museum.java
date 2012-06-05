package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;
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
	private CurrentUser userSession;

	@Inject
	private com.ngdb.entities.Museum museum;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@SetupRender
	public void init() {
		if (userSession.isAnonymous()) {
			games = gameFactory.findAll();
			hardwares = hardwareFactory.findAll();
		} else {
			User loggedUser = userSession.getUser();
			games = museum.findGamesOf(loggedUser);
			hardwares = museum.findHardwaresOf(loggedUser);
		}
	}
}
