package com.ngdb.web.pages.article;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Date;

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

import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.vo.Box;
import com.ngdb.entities.article.vo.Origin;
import com.ngdb.entities.article.vo.Platform;
import com.ngdb.entities.article.vo.Publisher;
import com.ngdb.web.model.BoxList;
import com.ngdb.web.model.GenreList;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.model.PublisherList;
import com.ngdb.web.services.domain.NotCreatedException;
import com.ngdb.web.services.domain.PictureService;
import com.ngdb.web.services.domain.ReferenceService;

public class GameUpdate {

	@Property
	@Persist("entity")
	private Game game;

	@Property
	protected UploadedFile mainPicture;

	@Inject
	protected ReferenceService referenceService;

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

	@Inject
	private Session session;

	private Logger log = LoggerFactory.getLogger(GameUpdate.class);

	void onActivate(Game game) {
		this.game = game;
		if (game != null) {
			this.publisher = game.getPublisher();
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
		return new PlatformList(referenceService.getPlatforms());
	}

	public SelectModel getGenres() {
		return new GenreList(referenceService.getGenres());
	}

	public SelectModel getPublishers() {
		return new PublisherList(referenceService.getPublishers());
	}

	public SelectModel getBoxes() {
		return new BoxList(referenceService.getBoxes());
	}

	public SelectModel getOrigins() {
		return new OriginList(referenceService.getOrigins());
	}

}
