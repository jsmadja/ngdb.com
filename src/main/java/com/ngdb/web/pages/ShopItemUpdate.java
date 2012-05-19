package com.ngdb.web.pages;

import java.util.Arrays;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.ShopItem;
import com.ngdb.entities.State;
import com.ngdb.web.model.CurrencyList;
import com.ngdb.web.model.StateList;

public class ShopItemUpdate {

	@Property
	protected UploadedFile mainPicture;

	@Inject
	protected Session session;

	@Property
	@Persist
	protected String url;

	@Property
	@Persist("entity")
	private ShopItem shopItem;

	void onActivate(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	@CommitAfter
	Object onSuccess() {
		session.merge(shopItem);
		return Index.class;
	}

	public SelectModel getStates() {
		return new StateList(session.createCriteria(State.class).list());
	}

	public SelectModel getCurrencies() {
		return new CurrencyList(Arrays.asList("USD"));
	}

}
