package com.ngdb.web.pages;

import static org.hibernate.criterion.Projections.countDistinct;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.ngdb.entities.article.Game;
import com.ngdb.entities.shop.Wish;

public class Index {

	@Inject
	private Session session;

	public Long getNumGames() {
		return (Long) session.createCriteria(Game.class).setProjection(Projections.rowCount()).uniqueResult();
	}

	public Long getNumWhishes() {
		return (Long) session.createCriteria(Wish.class).setProjection(countDistinct("article")).uniqueResult();
	}

}
