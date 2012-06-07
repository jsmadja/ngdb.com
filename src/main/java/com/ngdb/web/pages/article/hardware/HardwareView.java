package com.ngdb.web.pages.article.hardware;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.user.User;
import com.ngdb.web.pages.article.ArticleView;

public class HardwareView extends ArticleView {

	@Persist("entity")
	private Hardware hardware;

	@Property
	private Note property;

	@Property
	private Tag tag;

	@Property
	private Review review;

	@Property
	private String value;

	@Property
	private Note note;

	@Property
	private User user;

	public void onActivate(Hardware hardware) {
		this.hardware = hardware;
		this.user = userSession.getUser();
	}

	@Override
	protected Article getArticle() {
		return hardware;
	}

	public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}

	public Hardware getHardware() {
		return hardware;
	}

}
