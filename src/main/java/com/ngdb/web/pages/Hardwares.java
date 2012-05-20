package com.ngdb.web.pages;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Restrictions.between;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.Origin;
import com.ngdb.entities.article.Platform;

public class Hardwares {

	@Property
	private Hardware hardware;

	@Property
	private Collection<Hardware> hardwares;

	@Inject
	private Session session;

	private enum Filter {
		none, byReleaseDate, byPlatform, byOrigin
	};

	private Filter filter = Filter.none;

	private String filterValue;

	private Long id;

	private Predicate<Hardware> additionnalFilter;

	void onActivate(String filter, String value) {
		if (StringUtils.isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
			if (StringUtils.isNumeric(filterValue)) {
				id = Long.valueOf(filterValue);
			}
		}
	}

	@SetupRender
	void init() {
		Criteria criteria = createCriteria();
		this.hardwares = criteria.list();
		if (additionnalFilter != null) {
			this.hardwares = Collections2.filter(this.hardwares, additionnalFilter);
		}
	}

	private Criteria createCriteria() {
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
		return criteria;
	}

}
