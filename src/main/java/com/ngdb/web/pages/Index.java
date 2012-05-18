package com.ngdb.web.pages;

import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.countDistinct;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.ngdb.entities.Article;
import com.ngdb.entities.Game;
import com.ngdb.entities.ShopItem;
import com.ngdb.entities.Wish;

public class Index {

	private Game mostOwnedGame;
	private Game mostWantedGame;
	private Article lastForSaleArticle;

	@Inject
	private Session session;

	@SetupRender
	void init() {
		List games = findGames();
		mostOwnedGame = (Game) games.get(RandomUtils.nextInt(games.size()));
		mostWantedGame = (Game) findGames().get(RandomUtils.nextInt(games.size()));
		ShopItem lastShopItem = (ShopItem) session.createCriteria(ShopItem.class).setMaxResults(1).addOrder(desc("creationDate")).uniqueResult();
		lastForSaleArticle = lastShopItem.getArticle();
		if (lastForSaleArticle == null) {
			lastForSaleArticle = (Game) findGames().get(RandomUtils.nextInt(games.size()));
		}
	}

	private List findGames() {
		return session.createCriteria(Game.class).list();
	}

	public Long getNumGames() {
		return (Long) session.createCriteria(Game.class).setProjection(Projections.rowCount()).uniqueResult();
	}

	public Long getNumWhishes() {
		return (Long) session.createCriteria(Wish.class).setProjection(countDistinct("article")).uniqueResult();
	}

	public Game getMostOwnedGame() {
		return mostOwnedGame;
	}

	public Game getMostWantedGame() {
		return mostWantedGame;
	}

	public Article getLastForSaleArticle() {
		return lastForSaleArticle;
	}

}
