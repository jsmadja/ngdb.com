package com.ngdb.web.pages.article;

import java.math.BigInteger;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.CollectionObject;
import com.ngdb.entities.article.Comment;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Genre;
import com.ngdb.entities.article.Note;
import com.ngdb.entities.article.Review;
import com.ngdb.entities.article.Tag;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;

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

	public void onActivate(Game game) {
		this.game = game;
	}

	@CommitAfter
	Object onActionFromCollection(Game game) {
		User currentUser = userService.getCurrentUser();
		CollectionObject collection = new CollectionObject(currentUser, game);
		session.merge(collection);
		return GameView.class;
	}

	@CommitAfter
	Object onActionFromWishList(Game game) {
		User currentUser = userService.getCurrentUser();
		Wish wish = new Wish(currentUser, game);
		session.merge(wish);
		return GameView.class;
	}

	@CommitAfter
	public Object onSuccess() {
		User user = userService.getCurrentUser();
		Comment comment = new Comment(commentText, user, getArticle());
		session.merge(comment);
		return GameView.class;
	}

	public String getCollectionRank() {
		List<Object[]> list = session.createSQLQuery("SELECT article_id,COUNT(*) FROM CollectionObject GROUP BY article_id ORDER BY COUNT(*) DESC").list();
		int rank = 1;
		for (Object[] o : list) {
			BigInteger articleId = (BigInteger) o[0];
			if (game.getId().equals(articleId.longValue())) {
				return Integer.toString(rank);
			}
		}
		return "N/A";
	}

	public String getWishRank() {
		List<Object[]> list = session.createSQLQuery("SELECT article_id,COUNT(*) FROM Wish GROUP BY article_id ORDER BY COUNT(*) DESC").list();
		int rank = 1;
		for (Object[] o : list) {
			BigInteger articleId = (BigInteger) o[0];
			if (game.getId().equals(articleId.longValue())) {
				return Integer.toString(rank);
			}
		}
		return "N/A";
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
