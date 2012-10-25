package com.ngdb.web.components.article;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class NoteBlock {

	@Property
	@Parameter
	private Article article;

	@Property
	private Note note;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private ArticleFactory articleFactory;

	private Collection<String> suggestionsName = new TreeSet<String>();

	@Property
	private String name;

	@Property
	private String text;

	@Inject
	private Registry registry;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone noteZone;

	void onActivate() {
		suggestionsName.addAll(registry.findAllPropertyNames());
	}

    @CommitAfter
    @OnEvent(value = EventConstants.SUCCESS, component = "noteForm")
    public void onSuccessFromNoteForm() {
        if (isNotBlank(name) && isNotBlank(text)) {
            article.updateModificationDate();
            currentUser.addPropertyOn(article, name, text);
        }
        ajaxResponseRenderer.addRender(noteZone);
    }

    public User getUser() {
		return currentUser.getUser();
	}

	public List<Note> getNotes() {
		List<Note> notes = new ArrayList<Note>(article.getNotes().all());
		Collections.sort(notes);
		return notes;
	}

	@OnEvent("provideCompletions")
	List<String> autoCompete(String partial) {
		if (suggestionsName.isEmpty()) {
			onActivate();
		}
		final String filterLowerCase = partial.toLowerCase();
		suggestionsName = Collections2.filter(suggestionsName, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return input.toLowerCase().startsWith(filterLowerCase);
			}
		});
		return new ArrayList<String>(suggestionsName);
	}

	public String getCssClass() {
		if (getNotes().isEmpty() && currentUser.isAnonymous()) {
			return "";
		}
		return "table table-striped table-bordered table-condensed";
	}

}
