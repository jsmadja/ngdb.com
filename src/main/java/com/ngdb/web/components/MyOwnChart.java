package com.ngdb.web.components;

import com.google.common.base.Predicate;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.highcharts.components.AbstractHighCharts;
import org.joda.time.DateMidnight;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Collections2.filter;

public class MyOwnChart extends AbstractHighCharts {

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private ReferenceService referenceService;

    public JSONObject getComponentOptions() {
        long t = System.currentTimeMillis();
        JSONObject chart = new JSONObject();
        chart.put("type", "line");
        chart.put("marginRight", 130);
        chart.put("marginBottom", 25);

        JSONObject title = new JSONObject();
        title.put("text", "Nombres de jeux par plateforme et par année");
        title.put("x", -20);

        JSONObject xAxis = new JSONObject().put("categories", getXAxisValues());
        JSONObject yAxis = new JSONObject().put("plotLines", new JSONArray(new JSONObject().put("values", 0).put("width", 0).put("color", "#808080")));
        List<Game> allGames = articleFactory.findAllGames();
        JSONArray series = new JSONArray();
        for (final Platform platform : referenceService.getPlatforms()) {
            JSONArray value = getValuesForPlatform(allGames, platform);
            series.put(new JSONObject().put("name", platform.getName()).put("data", value));
        }

        JSONObject legend = new JSONObject();
        legend.put("layout", "vertical").
                put("align", "left").
                put("verticalAlign", "top").
                put("x", 400).
                put("y", 45).
                put("background", "#FFFFFF").
                put("floating", true);

        JSONObject high = new JSONObject();
        high.put("title", title).
                put("series", series).
                put("legend", legend).
                put("xAxis", xAxis).
                put("yAxis", yAxis).
                put("chart", new JSONObject("renderTo", getClientId(), "type", "line"));

        System.err.println("Generation time : "+ (System.currentTimeMillis() - t)+" ms");


        return high;
    }

    private JSONArray getValuesForPlatform(List<Game> allGames, Platform platform) {
        JSONArray value = new JSONArray();
        for (Integer year = 1990; year < 2005; year++) {
            int numGames = getNumGamesForPlatformAndYear(allGames, platform, year);
            value.put(numGames);
        }
        return value;
    }

    private int getNumGamesForPlatformAndYear(List<Game> allGames, final Platform platform, int year) {
        final Date startDate = new DateMidnight(year, 1, 1).toDate();
        final Date endDate = new DateMidnight(year, 12, 31).toDate();
        Collection<Game> gamesInPlatform = filter(allGames, new Predicate<Game>() {
            @Override
            public boolean apply(@Nullable Game game) {
                return game.getPlatformShortName().equalsIgnoreCase(platform.getShortName());
            }
        });
        return filter(gamesInPlatform, new Predicate<Game>() {
            @Override
            public boolean apply(@Nullable Game game) {
                return game.getReleaseDate().after(startDate) && game.getReleaseDate().before(endDate);
            }

        }).size();
    }

    private JSONArray getXAxisValues() {
        JSONArray years = new JSONArray();
        for (Integer i = 1990; i < 2005; i++) {
            years.put(i.toString());
        }
        return years;
    }
}