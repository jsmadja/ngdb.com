package com.ngdb.web.pages.shop;

import java.util.Arrays;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.model.CurrencyList;
import com.ngdb.web.model.StateList;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.domain.ReferenceService;

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

	@Inject
	private ReferenceService referenceService;

	void onActivate(ShopItem shopItem) {
		this.shopItem = shopItem;
	}

	@CommitAfter
	Object onSuccess() {
		session.merge(shopItem);
		return Index.class;
	}

	public SelectModel getStates() {
		return new StateList(referenceService.getStates());
	}

	public SelectModel getCurrencies() {
		return new CurrencyList(referenceService.getCurrencies());
	}

}
