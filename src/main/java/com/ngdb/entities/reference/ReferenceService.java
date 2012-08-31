package com.ngdb.entities.reference;

import static java.util.Arrays.asList;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.element.Tag;

@SuppressWarnings("unchecked")
public class ReferenceService {

	@Inject
	private Session session;

	public List<Platform> getPlatforms() {
		return session.createCriteria(Platform.class).setCacheable(true).addOrder(asc("name")).list();
	}

	public List<Publisher> getPublishers() {
		return session.createCriteria(Publisher.class).setCacheable(true).addOrder(asc("name")).list();
	}

	public List<Box> getBoxes() {
		return session.createCriteria(Box.class).setCacheable(true).list();
	}

	public List<Origin> getOrigins() {
		return session.createCriteria(Origin.class).setCacheable(true).addOrder(asc("title")).list();
	}

	public List<State> getStates() {
		return session.createCriteria(State.class).setCacheable(true).list();
	}

	public List<String> getCurrencies() {
		return asList("$");
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

	public Platform findPlatformByName(String platformName) {
        List<Platform> platforms = session.createCriteria(Platform.class).setCacheable(true).list();
        for (Platform platform : platforms) {
            String shortName = com.ngdb.web.components.common.Platform.shortNames.get(platform.getName());
            if(shortName.equalsIgnoreCase(platformName)) {
                return platform;
            }
        }
        return null;
	}

	public Origin findOriginByTitle(String originTitle) {
        List<Origin> origins = session.createCriteria(Origin.class).setCacheable(true).list();
        for (Origin origin : origins) {
            if(origin.getTitle().equalsIgnoreCase(originTitle)) {
                return origin;
            }
        }
        return null;
	}

	public Publisher findPublisherByName(String publisher) {
		return (Publisher) session.createCriteria(Publisher.class).add(eq("name", publisher)).setCacheable(true).uniqueResult();
	}

	public State findStateByTitle(String title) {
		return (State) session.createCriteria(State.class).add(eq("title", title)).setCacheable(true).uniqueResult();
	}

	public List<State> findAllStates() {
		return session.createCriteria(State.class).setCacheable(true).list();
	}

	public Tag findTagById(Long id) {
		return (Tag) session.load(Tag.class, id);
	}

}
