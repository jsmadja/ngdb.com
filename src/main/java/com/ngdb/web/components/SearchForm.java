package com.ngdb.web.components;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Suggestionner;
import com.ngdb.web.pages.Result;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class SearchForm {

	@Property
	private String search;

	@InjectPage
	private Result result;

	@Inject
	private Suggestionner suggestionner;

	Object onSuccess() {
		if (isNotBlank(search)) {
			result.setSearch(search);
			return result;
		}
		return null;
	}

	@OnEvent("provideCompletions")
	List<String> autoCompete(String partial) {
		final String filterLowerCase = partial.toLowerCase();
		return suggestionner.autoComplete(filterLowerCase);
	}

}
