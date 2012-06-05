package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Hardwares {

	@Property
	private Hardware hardware;

	@Property
	private Collection<Hardware> hardwares;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private CurrentUser userSession;

	@Inject
	private Session session;

	@Inject
	private WishBox wishBox;

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
		this.hardwares = hardwareFactory.findAll();
	}

	public boolean isAddableToCollection() {
		return userSession.canAddToCollection(hardware);
	}

	public boolean isWishable() {
		return userSession.canWish(hardware);
	}

	@CommitAfter
	Object onActionFromCollection(Hardware hardware) {
		User user = userSession.getUser();
		CollectionObject collectionObject = new CollectionObject(user, hardware);
		session.persist(collectionObject);
		user.addInCollection(collectionObject);
		return this;
	}

	@CommitAfter
	Object onActionFromWish(Game game) {
		User user = userSession.getUser();
		wishBox.add(user, game);
		return this;
	}

}
