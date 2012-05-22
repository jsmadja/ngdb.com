package com.ngdb.web.pages.shop;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.vo.State;
import com.ngdb.entities.shop.GameShopItem;
import com.ngdb.entities.shop.HardwareShopItem;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.model.CurrencyList;
import com.ngdb.web.model.StateList;
import com.ngdb.web.pages.Index;
import com.ngdb.web.services.domain.ReferenceService;
import com.ngdb.web.services.domain.UserService;

public class SellView {

	@Persist
	@Property
	private Article article;

	@Inject
	private Session session;

	@Property
	private UploadedFile mainPicture;

	@Persist
	@Property
	private String url;

	@Property
	private Double price;

	@Property
	private State state;

	@Property
	private String details;

	@Inject
	private UserService userService;

	@Inject
	private ReferenceService referenceService;

	void onActivate(Article article) {
		this.article = article;
	}

	@CommitAfter
	Object onSuccess() {
		Picture picture = null;
		if (StringUtils.isNotBlank(url)) {
			picture = new Picture(url);
		}
		User user = userService.getCurrentUser();
		ShopItem shopItem;
		if (article instanceof Game) {
			shopItem = new GameShopItem(picture, (Game) article, price, state, details, user);
		} else {
			shopItem = new HardwareShopItem(picture, (Hardware) article, price, state, details, user);
		}
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
