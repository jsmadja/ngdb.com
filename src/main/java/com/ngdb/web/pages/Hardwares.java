package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.article.Hardware;
import com.ngdb.web.Filter;
import com.ngdb.web.services.domain.HardwareService;

public class Hardwares {

	@Property
	private Hardware hardware;

	@Property
	private Collection<Hardware> hardwares;

	@Inject
	private HardwareService hardwareService;

	private Filter filter = Filter.none;

	private String filterValue;

	void onActivate(String filter, String value) {
		if (StringUtils.isNotBlank(filter)) {
			this.filter = Filter.valueOf(Filter.class, filter);
			this.filterValue = value;
		}
	}

	@SetupRender
	void init() {
		this.hardwares = hardwareService.findAll(filter, filterValue);
	}

}
