package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;

public class EditButton {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private boolean asButton;

	public String getUpdatePage() {
		if (article instanceof Game) {
			return "article/game/gameUpdate";
		}
		return "article/hardware/hardwareUpdate";
	}

}
