package com.ngdb.web.pages;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.Article;
import com.ngdb.entities.Game;
import com.ngdb.entities.GameShopItem;
import com.ngdb.entities.Picture;
import com.ngdb.entities.State;
import com.ngdb.entities.User;
import com.ngdb.web.model.StateList;
import com.ngdb.web.services.UserService;

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
		GameShopItem gameShopItem = new GameShopItem(picture, (Game) article, price, state, details, user);
		session.merge(gameShopItem);
		return Index.class;
	}

	public SelectModel getStates() {
		return new StateList(session.createCriteria(State.class).list());
	}

}
