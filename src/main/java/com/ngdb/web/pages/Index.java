package com.ngdb.web.pages;

import com.ngdb.entities.*;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Game;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

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

}
