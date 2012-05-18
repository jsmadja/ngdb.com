package com.ngdb.web.pages;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Game;
import com.ngdb.entities.Genre;
import com.ngdb.entities.Picture;
import com.ngdb.entities.Platform;
import com.ngdb.entities.Publisher;
import com.ngdb.web.model.GenreList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.model.PublisherList;

public class GameUpdate extends ArticleUpdate {

	@Property
	@Persist("entity")
	private Game game;

	@Inject
	private Session session;

	void onActivate(Game game) {
		this.game = game;
	}

	@CommitAfter
	Object onSuccess() {
		if (StringUtils.isNotBlank(url)) {
			Picture picture = new Picture(url);
			game.addPicture(picture);
		}
		return Index.class;
	}

	public SelectModel getPlatforms() {
		return new PlatformList(session.createCriteria(Platform.class).list());
	}

	public SelectModel getGenres() {
		return new GenreList(session.createCriteria(Genre.class).list());
	}

	public SelectModel getPublishers() {
		return new PublisherList(session.createCriteria(Publisher.class).list());
	}

	public void setGenres(Set<Genre> genres) {

	}

}
