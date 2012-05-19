package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Article;
import com.ngdb.entities.CollectionObject;
import com.ngdb.entities.Comment;
import com.ngdb.entities.Hardware;
import com.ngdb.entities.Note;
import com.ngdb.entities.Review;
import com.ngdb.entities.Tag;
import com.ngdb.entities.User;

public class HardwareView extends ArticleView {

	@Property
	@Persist("entity")
	private Hardware hardware;

	@Property
	private Note property;

	@Property
	private Tag tag;

	@Property
	private Review review;

	@Property
	private Comment comment;

	@Property
	private String value;

	@Property
	private Note note;

	@Inject
	protected Session session;

	@Property
	private String commentText;

	@CommitAfter
	Object onActionFromCollection(Hardware game) {
		User currentUser = userService.getCurrentUser();
		CollectionObject collection = new CollectionObject(currentUser, game);
		session.merge(collection);
		game.addOwner(collection);
		session.merge(game);
		return HardwareView.class;
	}

	public void onActivate(Hardware hardware) {
		this.hardware = hardware;
	}

	@CommitAfter
	public Object onSuccess() {
		User user = userService.getCurrentUser();
		session.merge(new Comment(commentText, user, getArticle()));
		return GameView.class;
	}

	@Override
	protected Article getArticle() {
		return hardware;
	}

}
