package com.ngdb.web.pages;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.HardwareFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;

public class Museum {

	@Property
	private Article game;

	@Property
	private Article hardware;

	@Property
	private Collection<? extends Article> hardwares;

	private List<Platform> platforms;

	@Property
	private Platform platform;

	private List<Origin> origins;

	@Property
	private Origin origin;

	@Inject
	private GameFactory gameFactory;

	@Inject
	private HardwareFactory hardwareFactory;

	@Inject
	private Population population;

	@Inject
	private ReferenceService referenceService;

	private int idTab = 0;
	private int idContent = 0;
	private int originIdTab = 0;
	private int originIdContent = 0;

	private Long id;

	@Property
	private User user;

	void onActivate() {
		this.origins = referenceService.getOrigins();
		this.platforms = referenceService.getPlatforms();
	}

	void onActivate(User user) {
		this.id = user.getId();
		this.user = user;
	}

	@SetupRender
	public void init() {
		if (id == null) {
			hardwares = hardwareFactory.findAll();
		} else {
			User user = population.findById(id);
			hardwares = user.getHardwaresInCollection();
		}
	}

	public Collection<Article> getGames() {
		Collection<Article> games = gameFactory.findAllByOriginAndPlatform(origin, platform);
		if (id != null) {
			final User user = population.findById(id);
			games = Collections2.filter(games, new Predicate<Article>() {
				@Override
				public boolean apply(Article article) {
					return user.owns(article);
				}
			});
		}
		return games;
	}

	public int getNumInPlatform() {
		return gameFactory.findAllByPlatform(platform).size();
	}

	public int getNumInOriginAndPlatform() {
		return getGames().size();
	}

	public List<Origin> getOrigins() {
		return origins;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public int getIdContent() {
		return idContent++;
	}

	public int getOriginIdContent() {
		return originIdContent++;
	}

	public int getIdTab() {
		return idTab++;
	}

	public int getOriginIdTab() {
		return originIdTab++;
	}

	public String getFirstTab() {
		return idTab == 0 ? "active" : "";
	}

	public String getFirstContent() {
		return idContent == 0 ? "active" : "";
	}

	public String getFirstOrigin() {
		return origin.getTitle().equalsIgnoreCase("Japan") ? "active" : "";
	}

}
