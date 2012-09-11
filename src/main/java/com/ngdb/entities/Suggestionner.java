package com.ngdb.entities;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.article.element.Tags;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static com.google.common.collect.Collections2.filter;

public class Suggestionner {

	private static final Logger LOG = LoggerFactory.getLogger(Suggestionner.class);

	private static Cache cache;
    public static final int MAX_SUGGESTIONS = 10;

    static {
		CacheManager create = CacheManager.create();
		cache = create.getCache("search.all.suggestions");
	}

	@Inject
	private Registry registry;

    @Inject
    private GameFactory gameFactory;

	private Collection<String> populateCache() {
		Collection<String> suggestions = createAllSuggestions();
		cache.put(new Element("suggestions", suggestions));
		LOG.info("Populate suggestions with " + suggestions.size() + " words");
		return suggestions;
	}

	private Collection<String> createAllSuggestions() {
		Collection<String> suggestions = new TreeSet<String>();
		for (Game game : gameFactory.findAll()) {
			suggestions.addAll(insertGameInformations(game));
		}
		return suggestions;
	}

	private Collection<String> insertGameInformations(Game game) {
		Collection<String> suggestions = new TreeSet<String>();
		suggestions.add(game.getTitle());
		suggestions.addAll(insertTags(game));
		return suggestions;
	}

	private Collection<String> insertTags(Game game) {
		Collection<String> suggestions = new TreeSet<String>();
		Tags tags = game.getTags();
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
		suggestions = filter(suggestions, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.toLowerCase().startsWith(partial);
            }
        });
		return new ArrayList<String>(suggestions).subList(0, suggestions.size()> MAX_SUGGESTIONS ? MAX_SUGGESTIONS :suggestions.size());
	}

}
