package com.ngdb.entities.reference;

import com.ngdb.entities.article.element.Tag;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.eq;

@SuppressWarnings("unchecked")
public class ReferenceService {

	@Inject
	private Session session;

	public List<Platform> getPlatforms() {
		return session.createCriteria(Platform.class).setCacheable(true).addOrder(asc("name")).list();
	}

	public List<Publisher> getPublishers() {
        List list = session.createCriteria(Publisher.class).setCacheable(true).list();
        Collections.sort(list);
        return list;
	}

	public List<Origin> getOrigins() {
        List list = session.createCriteria(Origin.class).setCacheable(true).list();
        Collections.sort(list);
        return list;
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
            String shortName = platform.getShortName();
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
