package com.ngdb.web.pages;

import com.ngdb.Comparators;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.services.HibernateSearchService;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Comparators.gamesByNghTitlePlatformOrigin;
import static java.util.Collections.sort;
import static org.fest.util.Strings.isEmpty;

public class Home {

    public static final int MIN_LENGTH = 3;
    @Property
    private String search;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone resultZone;

    @Inject
    private ReferenceService referenceService;

    @Property
    private List<Platform> platforms;

    @Property
    private Platform platform;

    @Property
    private Game game;

    @Inject
    private PictureService pictureService;

    @Inject
    private HibernateSearchService hibernateSearchService;

    private List<Game> games = new ArrayList<Game>();

    @Inject
    private Request request;

    private static List<String> EMPTY = new ArrayList<String>();

    void onActivate() {
        platforms = referenceService.getPlatforms();
    }

    @OnEvent(component = "searchForm", value = EventConstants.SUCCESS)
    public void onSuccessFromSearchForm() {
        if(isEmpty(search) || search.length() < MIN_LENGTH) {
            this.games = new ArrayList<Game>();
        } else {
            this.games = hibernateSearchService.searchGames(search);
        }
        ajaxResponseRenderer.addRender(resultZone);
    }

    public Collection<Game> getGames() {
        List<Game> games = new ArrayList<Game>(filter(this.games, new Result.PlatformPredicate(platform)));
        sort(games, gamesByNghTitlePlatformOrigin);
        return games;
    }

    public String getUrl() {
        Picture cover = pictureService.getCoverOf(game);
        return cover.getUrl("medium");
    }

    public boolean getHasResult() {
        return !games.isEmpty();
    }

    public int getCount() {
        return getGames().size();
    }

    public int getWidth() {
        return 200;
    }

    public int getHeight() {
        String platformShortName = game.getPlatformShortName();
        if(platformShortName.equals("AES")) {
            return 250;
        }
        if(platformShortName.equals("MVS")) {
            return 120;
        }
        return 200;
    }

}