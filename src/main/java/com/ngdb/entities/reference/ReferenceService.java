package com.ngdb.entities.reference;

import com.ngdb.entities.article.element.Tag;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.eq;

@SuppressWarnings("unchecked")
public class ReferenceService {

	@Inject
	private Session session;

    private static Cache cache;

    static {
        CacheManager create = CacheManager.create();
        cache = create.getCache("eternal");
    }

    public List<Platform> getPlatforms() {
        Element platforms = cache.get("platforms");
        if(platforms == null) {
            List list = session.createCriteria(Platform.class).setCacheable(true).addOrder(asc("name")).list();
            cache.put(new Element("platforms", list));
            return (List<Platform>) cache.get("platforms").getValue();
        }
        return (List<Platform>) platforms.getValue();
    }

	public List<Publisher> getPublishers() {
        Element publishers = cache.get("publishers");
        if(publishers == null) {
            List list = session.createCriteria(Publisher.class).setCacheable(true).list();
            Collections.sort(list);
            cache.put(new Element("publishers", list));
            return (List<Publisher>) cache.get("publishers").getValue();
        }
        return (List<Publisher>) publishers.getValue();
    }

	public List<Origin> getOrigins() {
        Element origins = cache.get("origins");
        if(origins == null) {
            List list = session.createCriteria(Origin.class).setCacheable(true).list();
            Collections.sort(list);
            cache.put(new Element("origins", list));
            return (List<Origin>) cache.get("origins").getValue();
        }
        return (List<Origin>) origins.getValue();
	}

	public List<State> getStates() {
		return session.createCriteria(State.class).setCacheable(true).list();
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
