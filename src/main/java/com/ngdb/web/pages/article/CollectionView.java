package com.ngdb.web.pages.article;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.domain.CollectionService;
import com.ngdb.web.services.domain.UserService;

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
	private UserService userService;

	@Inject
	private CollectionService collectionService;

	@SetupRender
	public void init() {
		User loggedUser = userService.getCurrentUser();
		List collectionObjects = collectionService.findCollectionOf(loggedUser);

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
