package com.ngdb.entities;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.rowCount;
import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.user.User;

@SuppressWarnings("unchecked")
public class HardwareFactory {

	@Inject
	private Session session;

	public Collection<Hardware> findAllByReleaseDate(Date releaseDate) {
		return allHardwares().add(between("releaseDate", releaseDate, releaseDate)).list();
	}

	public Collection<Hardware> findAllByPlatform(Platform platform) {
		return allHardwares().add(eq("platform", platform)).list();
	}

	public Collection<Hardware> findAllByOrigin(Origin origin) {
		return allHardwares().add(eq("origin", origin)).list();
	}

	public Long getNumHardwares() {
		return (Long) session.createCriteria(Hardware.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
	}

	public List<Hardware> findAll() {
		return allHardwares().list();
	}

	private Criteria allHardwares() {
		return session.createCriteria(Hardware.class).addOrder(asc("title"));
	}

	public List<Article> findAllOwnedBy(final User owner) {
		return new ArrayList<Article>(Collections2.filter(findAll(), new Predicate<Article>() {
			@Override
			public boolean apply(Article input) {
				return owner.owns(input);
			}
		}));
	}

}
