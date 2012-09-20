package com.ngdb.web.components;

import com.ngdb.entities.Suggestionner;
import com.ngdb.web.pages.Result;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class SearchForm {

    @Persist
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

    private static final String[] PLACEHOLDERS = new String[]{
        "Find games...",
        "Real Bout, Waku waku",
        "Fatal fury platform:aes",
        "Fatal fury p:aes",
        "Fatal fury origin:japan",
        "Fatal fury o:aes",
        "Fatal fury p:aes o:japan"
    };

    public String getPlaceholder() {
        return PLACEHOLDERS[RandomUtils.nextInt(PLACEHOLDERS.length)];
    }

}
