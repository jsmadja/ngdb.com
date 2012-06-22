package com.ngdb.entities;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.rowCount;
import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.Date;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;

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
		return (Long) session.createCriteria(Hardware.class).setCacheable(true).setProjection(rowCount()).uniqueResult();
	}

	public Collection<Hardware> findAll() {
		return allHardwares().list();
	}

	private Criteria allHardwares() {
		return session.createCriteria(Hardware.class).setCacheable(true).addOrder(asc("title"));
	}

}
