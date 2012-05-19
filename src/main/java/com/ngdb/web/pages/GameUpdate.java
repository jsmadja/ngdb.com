package com.ngdb.web.pages;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.Box;
import com.ngdb.entities.Game;
import com.ngdb.entities.Genre;
import com.ngdb.entities.Origin;
import com.ngdb.entities.Picture;
import com.ngdb.entities.Platform;
import com.ngdb.entities.Publisher;
import com.ngdb.web.model.BoxList;
import com.ngdb.web.model.GenreList;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.model.PublisherList;

public class GameUpdate {

	@Property
	@Persist("entity")
	private Game game;

	@Property
	protected UploadedFile mainPicture;

	@Inject
	protected Session session;

	@Property
	@Persist
	protected String url;

	@Property
	protected String details;

	@Property
	protected Origin origin;

	@Property
	protected Date releaseDate;

	@Property
	private Publisher publisher;

	@Property
	private Set<Genre> genres;

	@Property
	private Platform platform;

	@Property
	private Long megaCount;

	@Property
	private Box box;

	@Property
	protected String title;

	protected void onActivate(Game game) {
		this.game = game;
		if (game != null) {
			this.publisher = game.getPublisher();
			this.genres = game.getGenres();
			this.platform = game.getPlatform();
			this.megaCount = game.getMegaCount();
			this.box = game.getBox();
			this.details = game.getDetails();
			this.origin = game.getOrigin();
			this.releaseDate = game.getReleaseDate();
			this.details = game.getDetails();
			this.title = game.getTitle();
			this.url = game.getMainPicture().getUrl();
		}
	}

	@CommitAfter
	Object onSuccess() {
		Game game = new Game();
		game.setDetails(details);
		game.setOrigin(origin);
		game.setReleaseDate(releaseDate);
		game.setTitle(title);
		game.setPublisher(publisher);
		game.setGenres(genres);
		game.setPlatform(platform);
		game.setMegaCount(megaCount);
		game.setBox(box);
		Picture picture = Picture.EMPTY;
		if (StringUtils.isNotBlank(url)) {
			picture = new Picture(url);
		}
		game.addPicture(picture);
		session.merge(game);
		return Games.class;
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

	public SelectModel getBoxes() {
		return new BoxList(session.createCriteria(Box.class).list());
	}

	public SelectModel getOrigins() {
		return new OriginList(session.createCriteria(Origin.class).list());
	}

}
