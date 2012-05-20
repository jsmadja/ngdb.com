package com.ngdb.web.pages.article;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Date;
import java.util.Set;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngdb.entities.article.Box;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Genre;
import com.ngdb.entities.article.Origin;
import com.ngdb.entities.article.Picture;
import com.ngdb.entities.article.Platform;
import com.ngdb.entities.article.Publisher;
import com.ngdb.web.model.BoxList;
import com.ngdb.web.model.GenreList;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.model.PublisherList;
import com.ngdb.web.services.NotCreatedException;
import com.ngdb.web.services.PictureService;

public class GameUpdate {

	@Property
	@Persist("entity")
	private Game game;

	@Property
	protected UploadedFile mainPicture;

	@Inject
	protected Session session;

	@Inject
	protected PictureService pictureService;

	@Property
	@Persist
	protected String url;

	@Property
	protected String details;

	@Property
	@Validate("required")
	protected Origin origin;

	@Property
	@Validate("required")
	protected Date releaseDate;

	@Property
	@Validate("required")
	private Publisher publisher;

	@Property
	private Set<Genre> genres;

	@Property
	@Validate("required")
	private Platform platform;

	@Property
	@Validate("required")
	private Long megaCount;

	@Property
	@Validate("required")
	private Box box;

	@Property
	@Validate("required,maxLength=255")
	protected String title;

	@InjectPage
	private GameView gameView;

	private Logger log = LoggerFactory.getLogger(GameUpdate.class);

	void onActivate(Game game) {
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
		if (isEditMode()) {
			game = this.game;
		}
		game.setDetails(details);
		game.setOrigin(origin);
		game.setReleaseDate(releaseDate);
		game.setTitle(title);
		game.setPublisher(publisher);
		game.setGenres(genres);
		game.setPlatform(platform);
		game.setMegaCount(megaCount);
		game.setBox(box);
		game = (Game) session.merge(game);
		Picture picture = Picture.EMPTY;
		if (isNotBlank(url)) {
			try {
				picture = pictureService.store(url, game);
			} catch (NotCreatedException e) {
				log.error("Cannot create image for game:" + game.getId() + " with url':" + url + "'");
			}
		}
		game.addPicture(picture);
		gameView.setGame(game);
		return gameView;
	}

	public boolean isEditMode() {
		return this.game != null;
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
