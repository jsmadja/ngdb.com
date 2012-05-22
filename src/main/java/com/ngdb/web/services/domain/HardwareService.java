package com.ngdb.web.services.domain;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.vo.Origin;
import com.ngdb.entities.article.vo.Platform;
import com.ngdb.web.Filter;

public class HardwareService {

	@Inject
	private Session session;

	public Collection<Hardware> findAll(Filter filter, String filterValue) {
		Long id = null;
		Predicate<Hardware> additionnalFilter = null;
		if (StringUtils.isNumeric(filterValue)) {
			id = Long.valueOf(filterValue);
		}
		Criteria criteria = session.createCriteria(Hardware.class).addOrder(asc("title"));
		switch (filter) {
		case byReleaseDate:
			int year = Integer.valueOf(filterValue.split("-")[0]);
			Date searchDate = new DateTime().withTimeAtStartOfDay().withYear(year).toDate();
			criteria = criteria.add(between("releaseDate", searchDate, searchDate));
			break;
		case byPlatform:
			Platform platform = (Platform) session.load(Platform.class, id);
			criteria = criteria.add(eq("platform", platform));
			break;
		case byOrigin:
			Origin origin = (Origin) session.load(Origin.class, id);
			criteria = criteria.add(eq("origin", origin));
			break;
		}
		if (additionnalFilter != null) {
			return Collections2.filter(criteria.list(), additionnalFilter);
		}
		return criteria.list();
	}

}
