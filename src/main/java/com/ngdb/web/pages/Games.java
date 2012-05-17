package com.ngdb.web.pages;

import static org.hibernate.criterion.Restrictions.between;

import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.joda.time.DateTime;

import com.ngdb.entities.Game;

public class Games {

	@Property
	private Game game;

	@Property
	private List<Game> games;

	@Persist
	@Property
	private Integer year;

	@Inject
	private Session session;

	void onActivate(String sort, String value) {
		System.err.println(sort);
		System.err.println(value);
		if (value != null) {
			// this.year = Integer.valueOf(value.split("-")[0]);
		}
	}

	@SetupRender
	void init() {
		Criteria criteria = session.createCriteria(Game.class);
		if (year != null) {
			Date searchDate = new DateTime().withTimeAtStartOfDay().withYear(year).toDate();
			criteria = criteria.add(between("releaseDate", searchDate, searchDate));
			// } else if (platform != null) {
			// criteria = criteria.add(eq("platform", platform));
		}
		this.games = criteria.list();
	}
}
