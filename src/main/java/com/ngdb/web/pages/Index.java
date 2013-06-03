package com.ngdb.web.pages;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.News;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Locale;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

public class Index {

    @Inject
    private ArticleFactory articleFactory;

    @Property
    private Game game1, game2, game3;

    @Inject
    private CurrentUser currentUser;

    private static List<String> supportedLanguages = Lists.newArrayList("fr", "de");

    @Inject
    private ReferenceService referenceService;

    @Inject
    private Session session;

    private List<Platform> platforms;

    private List<Origin> origins;

    void onActivate() {
        platforms = referenceService.getPlatforms();
        origins = referenceService.getOrigins();
    }

    @SetupRender
    public void init() {
        Platform platform = platforms.get(nextInt(platforms.size()));
        Origin origin = origins.get(nextInt(origins.size()));
        if(hasMoreThanTwoGames(platform, origin)) {
            game1 = findRandomGame1(platform, origin);
            game2 = findRandomGame2(platform, origin);
            game3 = findRandomGame3(platform, origin);
        } else {
            init();
        }
    }

    private boolean hasMoreThanTwoGames(Platform platform, Origin origin) {
        return articleFactory.findNumGames(platform, origin) > 2;
    }

    private Game findRandomGame1(Platform platform, Origin origin) {
        return articleFactory.getRandomGameWithMainPicture(platform, origin);
    }

    private Game findRandomGame2(Platform platform, Origin origin) {
        Game game2 = articleFactory.getRandomGameWithMainPicture(platform, origin);
        if (game2.getId().equals(game1.getId())) {
            return findRandomGame2(platform, origin);
        }
        return game2;
    }

    private Game findRandomGame3(Platform platform, Origin origin) {
        Game game3 = articleFactory.getRandomGameWithMainPicture(platform, origin);
        if (game3.getId().equals(game1.getId()) || game3.getId().equals(game2.getId())) {
            return findRandomGame3(platform, origin);
        }
        return game3;
    }

    public String getHtmlNews() {
        Locale locale = currentUser.getLocale();
        String userLanguage = locale.getLanguage();
        if (!supportedLanguages.contains(userLanguage)) {
            userLanguage = "en";
        }
        List<News> news = session.createCriteria(News.class).add(Restrictions.eq("language", userLanguage)).list();
        return Joiner.on("").join(news);
    }

}
