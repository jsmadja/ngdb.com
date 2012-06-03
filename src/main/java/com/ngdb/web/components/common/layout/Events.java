package com.ngdb.web.components.common.layout;

import java.util.Collection;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.web.pages.article.game.GameView;
import com.ngdb.web.pages.article.hardware.HardwareView;

public class Events {

	@Inject
	private Session session;

	@Property
	private Comment comment;

	@Property
	private Game release;

	@InjectPage
	private GameView gameView;

	@InjectPage
	private HardwareView hardwareView;

	@Inject
	private GameFactory gameFactory;

	@SuppressWarnings("unchecked")
	public Collection<Comment> getLastComments() {
		return session.createCriteria(Comment.class).addOrder(Order.desc("creationDate")).setMaxResults(3).list();
	}

	@SuppressWarnings("unchecked")
	public Collection<Game> getReleases() {
		return gameFactory.findAllByReleasedToday();
	}

	Object onActionFromComment(Article article) {
		if (article instanceof Game) {
			gameView.setGame((Game) article);
			return gameView;
		}
		hardwareView.setHardware((Hardware) article);
		return hardwareView;
	}

}
