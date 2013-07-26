package com.ngdb.web.components.charts;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.misc.charts.Categories;
import com.ngdb.web.misc.charts.Chart;
import com.ngdb.web.misc.charts.Serie;
import com.ngdb.web.misc.charts.Series;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.joda.time.DateMidnight;

import javax.annotation.Nullable;
import java.util.Collection;

import static com.google.common.collect.Collections2.filter;

public class AverageAESMegCountByYearsChart implements Chart {

    @Inject
    private ArticleFactory articleFactory;

    public Series getSeries() {
        Collection<Game> allGames = articleFactory.findAllGames();
        Collection<Game> japaneseAesGames = filter(allGames, new Predicate<Game>() {
            @Override
            public boolean apply(@Nullable Game game) {
                return game.isAES() && game.isJapanese();
            }
        });
        Series series = new Series();
        Serie serie = new Serie("AES - Japan");
        for (Integer year = 1990; year < 2005; year++) {
            int averageMegCount = computeAverageMegCount(japaneseAesGames, year);
            serie.add(averageMegCount);
        }
        series.add(serie);
        return series;
    }

    private int computeAverageMegCount(Collection<Game> games, final Integer year) {
        Collection<Game> gamesInYear = filter(games, new Predicate<Game>() {
            @Override
            public boolean apply(@Nullable Game game) {
                return new DateMidnight(game.getReleaseDate()).getYear() == year;
            }
        });
        double sumMegCount = 0;
        for (Game game : gamesInYear) {
            sumMegCount += game.getMegaCount();
        }
        return (int) (sumMegCount / gamesInYear.size());
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
        return "MEGS moyen";
    }

}
