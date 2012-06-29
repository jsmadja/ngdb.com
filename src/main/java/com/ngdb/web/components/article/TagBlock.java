package com.ngdb.web.components.article;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class TagBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	private Tag tag;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private GameFactory gameFactory;

	private Collection<String> suggestions = new TreeSet<String>();

	@Property
	private String search;

	@Inject
	private Registry registry;

	void onActivate() {
		suggestions.addAll(registry.findAllTags());
	}

	@CommitAfter
	public Object onSuccess() {
		if (isNotBlank(search)) {
			if (!article.containsTag(search)) {
				currentUser.addTagOn(article, search);
			}
		}
		return this;
	}

	public User getUser() {
		return currentUser.getUser();
	}

	public Set<Tag> getTags() {
		if (article instanceof Game) {
			Game game = (Game) article;
			Set<Tag> tags = new TreeSet<Tag>(game.getTags().all());
			List<Game> relatedGames = gameFactory.findAllByNgh(game.getNgh());
			for (Game relatedGame : relatedGames) {
				tags.addAll(relatedGame.getTags().all());
			}
			return tags;
		}
		return article.getTags().all();
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
