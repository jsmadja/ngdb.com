package com.ngdb.web.components.common.layout;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.web.pages.Market;
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

	@InjectPage
	private Market marketPage;

	@Property
	private Game update;

	private List<Game> updates;

	@Inject
	private Registry registry;

	public List<Game> getUpdates() {
		return registry.findLastUpdates();
	}

	@SuppressWarnings("unchecked")
	public Collection<Comment> getLastComments() {
		return session.createCriteria(Comment.class).setCacheable(true).setCacheRegion("cacheCount").addOrder(Order.desc("creationDate")).setMaxResults(3).list();
	}

	public Collection<Game> getReleases() {
		return gameFactory.findAllByReleasedThisMonth();
	}

	Object onActionFromComment(Article article) {
		if (article instanceof Game) {
			gameView.setGame((Game) article);
			return gameView;
		}
		hardwareView.setHardware((Hardware) article);
		return hardwareView;
	}

	public String getMonth() {
		return new SimpleDateFormat("MMMMM", Locale.UK).format(new Date());
	}

}
