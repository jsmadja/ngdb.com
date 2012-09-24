package com.ngdb.web.pages;

import com.ngdb.Comparators;
import com.ngdb.Predicates;
import com.ngdb.StarsUtil;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.services.HibernateSearchService;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Collections2.filter;

public class Result {

    @Persist
    @Property
    private Collection<Game> results;

    @Property
    private Game result;

    @Persist
    private String search;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private Session session;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private HibernateSearchService hibernateSearchService;

    @Inject
    private Registry registry;

    private static final Logger LOG = LoggerFactory.getLogger(Result.class);

    @SetupRender
    public void setup() {
        if(StringUtils.isNotBlank(search)) {
            search();
        }
    }

    private void search() {
        LOG.info(currentUser.getUsername() + " is searching for '" + search + "'");
        String query = search;
        Platform filterPlatform = null;
        Origin filterOrigin = null;
        Matcher matcher = Pattern.compile("(platform:[a-zA-Z]*)").matcher(query);
        if(matcher.find()) {
            String filter = matcher.group(1);
            String platformFilter = filter.split(":")[1];
            filterPlatform = referenceService.findPlatformByName(platformFilter);
            query = query.replaceAll(filter, "");
        }
        matcher = Pattern.compile("(p:[a-zA-Z]*)").matcher(query);
        if(matcher.find()) {
            String filter = matcher.group(1);
            String platformFilter = filter.split(":")[1];
            filterPlatform = referenceService.findPlatformByName(platformFilter);
            query = query.replaceAll(filter, "");
        }

        matcher = Pattern.compile("(origin:[a-zA-Z]*)").matcher(query);
        if(matcher.find()) {
            String filter = matcher.group(1);
            String originFilter = filter.split(":")[1];
            filterOrigin = referenceService.findOriginByTitle(originFilter);
            query = query.replaceAll(filter, "");
        }

        matcher = Pattern.compile("(o:[a-zA-Z]*)").matcher(query);
        if(matcher.find()) {
            String filter = matcher.group(1);
            String originFilter = filter.split(":")[1];
            filterOrigin = referenceService.findOriginByTitle(originFilter);
            query = query.replaceAll(filter, "");
        }

        query = query.trim();

        results = hibernateSearchService.searchGames(query);

        if(filterPlatform != null) {
            results = filter(results, new Predicates.PlatformPredicate(filterPlatform));
        }
        if(filterOrigin != null) {
            results = filter(results, new Predicates.OriginPredicate(filterOrigin));
        }
        results = new ArrayList<Game>(results);
        Collections.sort((List)results, Comparators.gamesByTitlePlatformOrigin);
    }

    public String getStars() {
        return StarsUtil.getStars(result, session, articleFactory);
    }

    public String getByPublisher() {
        return Filter.byPublisher.name();
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
