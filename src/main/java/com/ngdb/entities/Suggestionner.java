package com.ngdb.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Notes;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.article.element.Tags;

public class Suggestionner {

	private static final Logger LOG = LoggerFactory.getLogger(Suggestionner.class);

	private static Cache cache;

	static {
		CacheManager create = CacheManager.create();
		cache = create.getCache("search.all.suggestions");
	}

	@Inject
	private Registry registry;

	private Collection<String> populateCache() {
		Collection<String> suggestions = createAllSuggestions();
		cache.put(new Element("suggestions", suggestions));
		LOG.info("Populate suggestions with " + suggestions.size() + " words");
		return suggestions;
	}

	private Collection<String> createAllSuggestions() {
		Collection<String> suggestions = new TreeSet<String>();
		for (Article article : registry.findAllArticles()) {
			suggestions.addAll(insertArticleInformations(article));
		}
		return suggestions;
	}

	private Collection<String> insertArticleInformations(Article article) {
		Collection<String> suggestions = new TreeSet<String>();
		suggestions.add(article.getTitle());
		suggestions.addAll(insertTags(article));
		suggestions.addAll(insertNotes(article));
		return suggestions;
	}

	private Collection<String> insertNotes(Article article) {
		Collection<String> suggestions = new TreeSet<String>();
		Notes notes = article.getNotes();
		for (Note note : notes) {
			suggestions.add(note.getName());
		}
		return suggestions;
	}

	private Collection<String> insertTags(Article article) {
		Collection<String> suggestions = new TreeSet<String>();
		Tags tags = article.getTags();
		for (Tag tag : tags) {
			suggestions.add(tag.getName());
		}
		return suggestions;
	}

	public List<String> autoComplete(final String partial) {
		Element element = cache.get("suggestions");
		Collection<String> suggestions;
		if (element == null) {
			suggestions = populateCache();
		} else {
			suggestions = (Collection<String>) element.getValue();
		}
		suggestions = Collections2.filter(suggestions, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return input.toLowerCase().startsWith(partial);
			}
		});
		return new ArrayList<String>(suggestions);
	}

}
