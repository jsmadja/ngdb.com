package com.ngdb.web.pages.article;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.CollectionObject;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.UserService;

public class CollectionView {

	@Property
	private CollectionObject game;

	@Property
	private Collection<CollectionObject> games;

	@Property
	private CollectionObject hardware;

	@Property
	private Collection<CollectionObject> hardwares;

	@Inject
	private Session session;

	@Inject
	private UserService userService;

	@SetupRender
	public void init() {
		User loggedUser = userService.getCurrentUser();
		List collectionObjects = session.createCriteria(CollectionObject.class).add(eq("owner", loggedUser)).list();
		Collections.sort(collectionObjects);

		games = Collections2.filter(collectionObjects, new Predicate<CollectionObject>() {
			@Override
			public boolean apply(CollectionObject input) {
				return input.getArticle() instanceof Game;
			}
		});

		hardwares = Collections2.filter(collectionObjects, new Predicate<CollectionObject>() {
			@Override
			public boolean apply(CollectionObject input) {
				return input.getArticle() instanceof Hardware;
			}
		});
	}
}
