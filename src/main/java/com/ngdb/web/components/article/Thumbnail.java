package com.ngdb.web.components.article;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;

public class Thumbnail {

	@Property
	@Parameter
	private Article article;

	public String getViewPage() {
		if (article instanceof Game) {
			return "article/game/gameView";
		}
		return "article/hardware/hardwareView";
	}

}
