package com.ngdb.web.pages.base;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;

public class Redirections {

	public static String toViewPage(Article article) {
		if (article.getType().equals(Game.class)) {
			return "article/game/gameView";
		}
		return "article/hardware/hardwareView";
	}
}
