package com.ngdb.web.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.joda.time.DateTime;

import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Hardwares {

	@Property
	private Hardware hardware;

	@Property
	private Collection<Hardware> hardwares;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private ReferenceService referenceService;

	@Inject
	private CurrentUser currentUser;

	@Persist
	@Property
	private Filter filter;

	@Persist
	@Property
	private Long id;

	@Property
	private String filterValue;

	void onActivate() {
		filter = Filter.none;
	}

	boolean onActivate(String filter, String value) {
		if (isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
			if (StringUtils.isNumeric(filterValue)) {
				this.id = Long.valueOf(filterValue);
			}
		}
		return true;
	}

	@SetupRender
	void init() {
		switch (filter) {
		case byReleaseDate:
			int year = Integer.valueOf(filterValue.split("-")[0]);
			int month = Integer.valueOf(filterValue.split("-")[1]);
			int day = Integer.valueOf(filterValue.split("-")[2]);
			Date releaseDate = new DateTime().withTimeAtStartOfDay().withDayOfMonth(day).withMonthOfYear(month).withYear(year).toDate();
			this.hardwares = hardwareFactory.findAllByReleaseDate(releaseDate);
			break;
		case byOrigin:
			Origin origin = referenceService.findOriginById(id);
			this.filterValue = origin.getTitle();
			this.hardwares = hardwareFactory.findAllByOrigin(origin);
			break;
		case byPlatform:
			Platform platform = referenceService.findPlatformById(id);
			this.filterValue = platform.getName();
			this.hardwares = hardwareFactory.findAllByPlatform(platform);
			break;
		default:
			this.hardwares = hardwareFactory.findAll();
			break;
		}
	}

	public User getUser() {
		return currentUser.getUser();
	}

}
