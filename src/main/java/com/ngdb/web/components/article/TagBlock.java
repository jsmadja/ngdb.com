package com.ngdb.web.components.article;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class TagBlock {

    @Property
    @Parameter
    private Article article;

    @Property
    @Parameter
    private boolean hideForm;

    @Property
    private Tag tag;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private ArticleFactory articleFactory;

    private Collection<String> suggestions = new TreeSet<String>();

    @Property
    private String search;

    @Inject
    private Registry registry;

    @Inject
    private ActionLogger actionLogger;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone tagZone;

    void onActivate() {
        suggestions.addAll(registry.findAllTags());
    }

    @CommitAfter
    @OnEvent(value = EventConstants.SUCCESS, component = "tagForm")
    public void onSuccessFromTagForm(Article article) {
        this.article = article;
        if (isNotBlank(search)) {
            Set<String> tags = extractTags();
            for (String tag : tags) {
                addTag(tag);
            }
            actionLogger.addTagAction(getUser(), article);
        }
        this.search = "";
        ajaxResponseRenderer.addRender(tagZone);
    }

    private void addTag(String tag) {
        if (!article.containsTag(tag)) {
            article.updateModificationDate();
            currentUser.addTagOn(article, tag);
        }
    }

    private Set<String> extractTags() {
        Set<String> tags = new TreeSet<String>();
        String[] split = search.split(",");
        for (String tagsSplitByVirgule : split) {
            String[] tagsToAdd = tagsSplitByVirgule.split(";");
            for (String tag : tagsToAdd) {
                tags.add(tag.trim());
            }
        }
        return tags;
    }

    public User getUser() {
        return currentUser.getUser();
    }

    public Set<Tag> getTags() {
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

    public String getByTag() {
        return Filter.byTag.name();
    }

}
