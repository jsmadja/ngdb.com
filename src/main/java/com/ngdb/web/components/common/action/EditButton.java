package com.ngdb.web.components.common.action;

import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import org.apache.tapestry5.ioc.annotations.Inject;

public class EditButton {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private boolean asButton;

    @Inject
    private CurrentUser currentUser;

	public String getUpdatePage() {
		if (article instanceof Game) {
			return "article/game/gameUpdate";
		}
		return "article/hardware/hardwareUpdate";
	}

    public boolean getCanEdit() {
        return currentUser.isContributor();
    }

}
