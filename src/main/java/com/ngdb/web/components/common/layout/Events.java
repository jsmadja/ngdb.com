package com.ngdb.web.components.common.layout;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
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

import javax.annotation.Nullable;

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

	public Collection<Game> getUpdates() {
        final Set<String> added = new HashSet<String>();
        List<Game> lastUpdates = registry.findLastUpdates();
        return Collections2.filter(lastUpdates, new Predicate<Game>() {
            @Override
            public boolean apply(@Nullable Game input) {
                if(added.contains(input.getTitle())) {
                    return false;
                }
                added.add(input.getTitle());
                return true;
            }
        });
	}

	@SuppressWarnings("unchecked")
	public Collection<Comment> getLastComments() {
		return session.createCriteria(Comment.class).setCacheable(true).setCacheRegion("cacheCount").addOrder(Order.desc("creationDate")).setMaxResults(5).list();
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
