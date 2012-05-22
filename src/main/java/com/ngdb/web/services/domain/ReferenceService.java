package com.ngdb.web.services.domain;

import static java.util.Arrays.asList;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.vo.Box;
import com.ngdb.entities.article.vo.Genre;
import com.ngdb.entities.article.vo.Origin;
import com.ngdb.entities.article.vo.Platform;
import com.ngdb.entities.article.vo.Publisher;
import com.ngdb.entities.article.vo.State;

public class ReferenceService {

	@Inject
	private Session session;

	public List getPlatforms() {
		return session.createCriteria(Platform.class).list();
	}

	public List getGenres() {
		return session.createCriteria(Genre.class).list();
	}

	public List getPublishers() {
		return session.createCriteria(Publisher.class).list();
	}

	public List getBoxes() {
		return session.createCriteria(Box.class).list();
	}

	public List getOrigins() {
		return session.createCriteria(Origin.class).list();
	}

	public List<State> getStates() {
		return session.createCriteria(State.class).list();
	}

	public List<String> getCurrencies() {
		return asList("USD");
	}

}
