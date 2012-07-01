package com.ngdb.web.components.common.layout;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Game;

public class Menu {

	@Property
	private Game update;

	private List<Game> updates;

	@Inject
	private Registry registry;

	public List<Game> getUpdates() {
		return registry.findLastUpdates();
	}

}
