package com.ngdb.web.pages;

import com.google.common.collect.Collections2;
import com.ngdb.Predicates;
import com.ngdb.StarsUtil;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Collections2.filter;

public class Result {

    public static final int STAR_SIZE = 15;

    @Persist
    @Property
    private Collection<Game> results;

    @Property
    private Game result;

    @Persist
    private String search;

    @Inject
    private Registry registry;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private GameFactory gameFactory;

    @Inject
    private Session session;

    @Inject
    private ReferenceService referenceService;

    private static final Logger LOG = LoggerFactory.getLogger(Result.class);

    @SetupRender
    public void setup() {
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
        results = registry.findGamesMatching(query);

        if(filterPlatform != null) {
            results = filter(results, new Predicates.PlatformPredicate(filterPlatform));
        }
        if(filterOrigin != null) {
            results = filter(results, new Predicates.OriginPredicate(filterOrigin));
        }

    }

    public String getStars() {
        result = (Game) session.load(Game.class, result.getId());
        if (result.getHasReviews()) {
            String mark = result.getAverageMark();
            return StarsUtil.toStarsHtml(mark, 15);
        } else {
            String ngh = result.getNgh();
            List<Game> games = gameFactory.findAllByNgh(ngh);
            for (Game game : games) {
                if (game.getHasReviews()) {
                    String mark = game.getAverageMark();
                    return StarsUtil.toStarsHtml(mark, 15);
                }
            }
        }
        return StarsUtil.toStarsHtml("0", STAR_SIZE);
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
