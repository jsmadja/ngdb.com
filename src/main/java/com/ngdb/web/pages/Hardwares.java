package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.article.Hardware;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Hardwares {

	@Property
	private Hardware hardware;

	@Property
	private Collection<Hardware> hardwares;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private CurrentUser currentUser;

	@SetupRender
	void init() {
		this.hardwares = hardwareFactory.findAll();
	}

	public boolean isAddableToCollection() {
		return currentUser.canAddToCollection(hardware);
	}

	public boolean isWishable() {
		return currentUser.canWish(hardware);
	}

	@CommitAfter
	Object onActionFromCollection(Hardware hardware) {
		currentUser.addToCollection(hardware);
		return this;
	}

	@CommitAfter
	Object onActionFromWish(Hardware hardware) {
		currentUser.wish(hardware);
		return this;
	}

}
