package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.CollectionObject;
import com.ngdb.entities.Comment;
import com.ngdb.entities.Game;
import com.ngdb.entities.Genre;
import com.ngdb.entities.Note;
import com.ngdb.entities.Review;
import com.ngdb.entities.Tag;
import com.ngdb.entities.User;
import com.ngdb.web.services.UserService;

public class GameView {

	@Property
	@Persist("entity")
	private Game game;

	@Property
	private Note property;

	@Property
	private Tag tag;

	@Property
	private Review review;

	@Property
	private Genre genre;

	@Property
	private Comment comment;

	@Property
	private String value;

	@Property
	private Note note;

	@Inject
	private Session session;

	@Property
	private String commentText;

	@Inject
	private UserService userService;

	public void onActivate(Game game) {
		this.game = game;
	}

	@CommitAfter
	Object onActionFromCollection(Game game) {
		User currentUser = userService.getCurrentUser();
		CollectionObject collection = new CollectionObject(currentUser, game);
		session.merge(collection);
		game.addOwner(collection);
		session.merge(game);
		return GameView.class;
	}

	@CommitAfter
	public Object onSuccess() {
		User user = userService.getCurrentUser();
		session.merge(new Comment(commentText, user, game));
		return GameView.class;
	}

	public Game onPassivate() {
		return game;
	}

	public int getNumAvailableCopy() {
		return game.getAvailableCopyCount();
	}

	public int getSealedQuantity() {
		return 3;
	}

	public String getSealedAverage() {
		return "$3.00";
	}

	public String getSealedMax() {
		return "$30.00";
	}

	public int getMintQuantity() {
		return 3;
	}

	public String getMintAverage() {
		return "$3.00";
	}

	public String getMintMax() {
		return "$30.00";
	}

	public int getUsedQuantity() {
		return 3;
	}

	public String getUsedAverage() {
		return "$3.00";
	}

	public String getUsedMax() {
		return "$30.00";
	}

	public String getByPlatform() {
		return "byPlatform";
	}

	public String getByNgh() {
		return "byNgh";
	}

	public String getByGenre() {
		return "byGenre";
	}

	public String getByPublisher() {
		return "byPublisher";
	}

	public String getByOrigin() {
		return "byOrigin";
	}

	public String getByReleaseDate() {
		return "byReleaseDate";
	}

	public String getByArticle() {
		return "byArticle";
	}

	public boolean isAddableToCollection() {
		User user = userService.getCurrentUser();
		return user.canAddInCollection(game);
	}

	public boolean isBuyable() {
		return game.isBuyable();
	}

	public boolean isSellable() {
		User user = userService.getCurrentUser();
		return user.canSell(game);
	}

	public boolean isWishable() {
		User user = userService.getCurrentUser();
		return user.canWish(game);
	}
}
