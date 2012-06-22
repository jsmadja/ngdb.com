package com.ngdb.web.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

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

	private Collection<? extends Article> games;

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

	private Long id;

	void onActivate() {
		this.origins = referenceService.getOrigins();
		this.platforms = referenceService.getPlatforms();
	}

	boolean onActivate(String category, String value) {
		if (isNotBlank(category)) {
			if (StringUtils.isNumeric(value)) {
				id = Long.valueOf(value);
			}
		}
		return true;
	}

	@SetupRender
	public void init() {
		if (id == null) {
			games = gameFactory.findAll();
			hardwares = hardwareFactory.findAll();
		} else {
			User user = population.findById(id);
			games = user.getGamesInCollection();
			hardwares = user.getHardwaresInCollection();
		}
	}

	public Collection<? extends Article> getGames() {
		return gameFactory.findAllByOriginAndPlatform(origin, platform);
	}

	public List<Origin> getOrigins() {
		return origins;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}
}
