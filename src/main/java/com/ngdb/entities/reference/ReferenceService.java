package com.ngdb.entities.reference;

import static java.util.Arrays.asList;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class ReferenceService {

	@Inject
	private Session session;

	public List<Platform> getPlatforms() {
		return session.createCriteria(Platform.class).list();
	}

	public List<Genre> getGenres() {
		return session.createCriteria(Genre.class).list();
	}

	public List<Publisher> getPublishers() {
		return session.createCriteria(Publisher.class).list();
	}

	public List<Box> getBoxes() {
		return session.createCriteria(Box.class).list();
	}

	public List<Origin> getOrigins() {
		return session.createCriteria(Origin.class).list();
	}

	public List<State> getStates() {
		return session.createCriteria(State.class).list();
	}

	public List<String> getCurrencies() {
		return asList("USD");
	}

	public Genre findGenreById(Long id) {
		return (Genre) session.load(Genre.class, id);
	}

	public Origin findOriginById(Long id) {
		return (Origin) session.load(Origin.class, id);
	}

	public Platform findPlatformById(Long id) {
		return (Platform) session.load(Platform.class, id);
	}

	public Publisher findPublisherBy(Long id) {
		return (Publisher) session.load(Publisher.class, id);
	}

	public Platform findPlatformByName(String platform) {
		return (Platform) session.createCriteria(Platform.class).add(eq("name", platform)).uniqueResult();
	}

	public Origin findOriginByTitle(String origin) {
		return (Origin) session.createCriteria(Origin.class).add(eq("title", origin)).uniqueResult();
	}

	public Publisher findPublisherByName(String publisher) {
		return (Publisher) session.createCriteria(Publisher.class).add(eq("name", publisher)).uniqueResult();
	}

	public State findStateByTitle(String title) {
		return (State) session.createCriteria(State.class).add(eq("title", title)).uniqueResult();
	}

	public List<State> findAllStates() {
		return session.createCriteria(State.class).list();
	}

}
