package com.ngdb.web.pages;

import com.ngdb.StarsUtil;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
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

public class Result {

    @Persist
    @Property
    private List<Game> results;

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

    private static final Logger LOG = LoggerFactory.getLogger(Result.class);

    @SetupRender
    public void setup() {
        LOG.info(currentUser.getUsername() + " is searching for '" + search + "'");
        results = registry.findGamesMatching(search);
    }

    public String getViewPage() {
        return result.getViewPage();
    }

    public String getStars() {
        result = (Game) session.load(Game.class, result.getId());
        if (result.getHasReviews()) {
            String mark = result.getAverageMark();
            return StarsUtil.toStarsHtml(mark);
        } else {
            String ngh = result.getNgh();
            List<Game> games = gameFactory.findAllByNgh(ngh);
            for (Game game : games) {
                if (game.getHasReviews()) {
                    String mark = game.getAverageMark();
                    return StarsUtil.toStarsHtml(mark);
                }
            }
        }
        return StarsUtil.toStarsHtml("0");
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
