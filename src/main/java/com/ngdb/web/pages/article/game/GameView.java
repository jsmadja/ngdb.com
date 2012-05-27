package com.ngdb.web.pages.article.game;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Museum;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.user.User;
import com.ngdb.web.pages.article.ArticleView;

public class GameView extends ArticleView {

	@Persist("entity")
	private Game game;

	@Property
	private Genre genre;

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

	@Inject
	private Museum museum;

	@Inject
	private WishBox wishBox;

	public void onActivate(Game game) {
		this.game = game;
	}

	// @CommitAfter
	// Object onActionFromCollection(Game game) {
	// User loggedUser = userSession.getUser();
	// CollectionObject collection = new CollectionObject(loggedUser, game);
	// session.merge(collection);
	// return GameView.class;
	// }
	//
	// @CommitAfter
	// Object onActionFromWishList(Game game) {
	// User currentUser = userSession.getUser();
	// wishBox.add(currentUser, game);
	// return GameView.class;
	// }

	@CommitAfter
	public Object onSuccess() {
		User user = userSession.getUser();
		Comment comment = new Comment(commentText, user, getArticle());
		session.merge(comment);
		return GameView.class;
	}

	public String getCollectionRank() {
		return museum.getRankOf(game);
	}

	public String getWishRank() {
		return wishBox.getRankOf(game);
	}

	public Game onPassivate() {
		return game;
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

	@Override
	protected Game getArticle() {
		return game;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
