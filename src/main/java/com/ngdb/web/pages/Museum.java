package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.UserSession;

public class Museum {

	@Property
	private CollectionObject game;

	@Property
	private Collection<CollectionObject> games;

	@Property
	private CollectionObject hardware;

	@Property
	private Collection<CollectionObject> hardwares;

	@Inject
	private UserSession userSession;

	@Inject
	private com.ngdb.entities.Museum museum;

	@SetupRender
	public void init() {
		User loggedUser = userSession.getUser();
		games = museum.findGamesOf(loggedUser);
		hardwares = museum.findHardwaresOf(loggedUser);
	}
}
