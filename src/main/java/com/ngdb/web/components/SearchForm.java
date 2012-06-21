package com.ngdb.web.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.web.pages.Result;

public class SearchForm {

	@Inject
	private Registry registry;

	@Property
	private String search;

	@InjectPage
	private Result result;

	private Collection<String> suggestions = new TreeSet<String>();

	void onActivate() {
		Collection<Article> articles = registry.findAll();
		for (Article article : articles) {
			suggestions.add(article.getTitle());
		}
	}

	Object onSuccess() {
		if (StringUtils.isNotBlank(search)) {
			result.setSearch(search);
			return result;
		}
		return null;
	}

	@OnEvent("provideCompletions")
	List<String> autoCompete(String partial) {
		if (suggestions.isEmpty()) {
			onActivate();
		}
		final String filterLowerCase = partial.toLowerCase();
		suggestions = Collections2.filter(suggestions, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return input.toLowerCase().startsWith(filterLowerCase);
			}
		});
		return new ArrayList<String>(suggestions);
	}

}
