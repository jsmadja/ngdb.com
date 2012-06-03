package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.article.Article;
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

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@SetupRender
	public void init() {
		if (userSession.isAnonymous()) {
			Function<Article, CollectionObject> toCollectionObject = new Function<Article, CollectionObject>() {
				@Override
				public CollectionObject apply(Article article) {
					return new CollectionObject(User.GUEST, article);
				}
			};
			games = Collections2.transform(gameFactory.findAll(), toCollectionObject);
			hardwares = Collections2.transform(hardwareFactory.findAll(), toCollectionObject);
		} else {
			User loggedUser = userSession.getUser();
			games = museum.findGamesOf(loggedUser);
			hardwares = museum.findHardwaresOf(loggedUser);
		}
	}
}
