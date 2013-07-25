package com.ngdb.web.components.charts;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.misc.charts.Categories;
import com.ngdb.web.misc.charts.Chart;
import com.ngdb.web.misc.charts.Serie;
import com.ngdb.web.misc.charts.Series;
import org.apache.tapestry5.ioc.annotations.Inject;

public class NumGamesByYearsChart implements Chart {

    @Inject
    private ReferenceService referenceService;

    @Inject
    private ArticleFactory articleFactory;

    public Series getSeries() {
        Series series = new Series();
        for (Platform platform : referenceService.getPlatforms()) {
            Serie serie = new Serie(platform.getName());
            for (Integer year = 1990; year < 2005; year++) {
                Long numGames = articleFactory.getNumGamesForPlatformAndYear(platform, year);
                serie.add(numGames);
            }
            series.add(serie);
        }
        return series;
    }

    public Categories getCategories() {
        Categories categories = new Categories();
        for (Integer i = 1990; i < 2005; i++) {
            categories.add(i.toString());
        }
        return categories;
    }

    @Override
    public String getTitle() {
        return "Nombres de jeux par plateforme et par année";
    }

}
