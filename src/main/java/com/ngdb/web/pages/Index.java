package com.ngdb.web.pages;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.News;
import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Game;
import com.ngdb.web.services.infrastructure.CurrentUser;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Index {

	@Inject
	private WishBox wishBox;

	@Inject
	private Population population;

	@Inject
	private com.ngdb.entities.Market market;

    @Inject
    private ArticleFactory articleFactory;

	private static Cache cache;

    @Property
    private String randomGame1MainPicture;

    @Property
    private String randomGame2MainPicture;

    @Property
    private String randomGame3MainPicture;

    private List<String> urls = new ArrayList<String>();

    @Inject
    private CurrentUser currentUser;

    static {
		CacheManager create = CacheManager.create();
		cache = create.getCache("index.random.games");
	}

	@SetupRender
	public void init() {
	    randomGame1MainPicture = getRandomGame1().getCover().getUrl("medium");
        urls.add(randomGame1MainPicture);

        randomGame2MainPicture = getRandomGame2().getCover().getUrl("medium");
        urls.add(randomGame2MainPicture);

        randomGame3MainPicture = getRandomGame3().getCover().getUrl("medium");
    }

	public Game getRandomGame1() {
		return getRandomGameFromCache(1);
	}

	public Game getRandomGame2() {
		return getRandomGameFromCache(2);
	}

	public Game getRandomGame3() {
		return getRandomGameFromCache(3);
	}

	private Game getRandomGameFromCache(int index) {
		Element elementInCache = cache.get(index);
		if (elementInCache != null) {
			return (Game) elementInCache.getValue();
		}
		Game randomGame = articleFactory.getRandomGameWithMainPicture();
        String url = randomGame.getCover().getUrl("medium");
        if(urls.contains(url)) {
            return getRandomGameFromCache(index);
        }
		Element element = new Element(index, randomGame);
		cache.put(element);
		return getRandomGameFromCache(index);
	}

    public String getHtmlNews() {
        Locale locale = currentUser.getLocale();
        String userLanguage = locale.getLanguage();
        if(!supportedLanguages.contains(userLanguage)) {
            userLanguage = "en";
        }
        List<News> news = session.createCriteria(News.class).add(Restrictions.eq("language", userLanguage)).list();
        return Joiner.on("").join(news);
    }

    private static List<String> supportedLanguages = Lists.newArrayList("fr", "de");

    @Inject
    private Session session;

}
