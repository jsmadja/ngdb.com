package com.ngdb.web.components.common.action;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
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
		return article.getUpdatePage();
    }

    public boolean getCanEdit() {
        return currentUser.isContributor();
    }

}
