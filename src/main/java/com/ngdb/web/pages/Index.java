package com.ngdb.web.pages;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Game;

public class Index {

	@Inject
	private GameFactory gameFactory;

	@Inject
	private WishBox wishBox;

	@Property
	private Long articleCount;

	@Inject
	private HardwareFactory hardwareFactory;

	private static Cache cache;

	static {
		CacheManager create = CacheManager.create();
		cache = create.getCache("index.random.games");
	}

	@SetupRender
	public void init() {
		this.articleCount = gameFactory.getNumGames() + hardwareFactory.getNumHardwares();
	}

	public Long getNumWhishes() {
		return wishBox.getNumWishes();
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
		if (cache.isKeyInCache(index)) {
			return (Game) cache.get(index).getValue();
		}
		Game randomGame = gameFactory.getRandomGameWithMainPicture();
		Element element = new Element(index, randomGame);
		cache.put(element);
		return getRandomGameFromCache(index);
	}

	public String getRandomGame1MainPicture() {
		return getRandomGame1().getMainPicture().getUrl("medium");
	}

	public String getRandomGame2MainPicture() {
		return getRandomGame2().getMainPicture().getUrl("medium");
	}

	public String getRandomGame3MainPicture() {
		return getRandomGame3().getMainPicture().getUrl("medium");
	}

}
