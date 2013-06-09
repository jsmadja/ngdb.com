package com.ngdb.web.components.article;

import com.ngdb.entities.Reviewer;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import java.util.Collection;

public class Articles {

    @Property
    @Parameter(allowNull=false)
    private Collection<Article> articles;

    @Property
    private Article article;

    @Persist
    @Property
    private boolean thumbnailMode;

    @Persist
    @Property
    private boolean gridMode;

    @Persist
    @Property
    private boolean tableMode;

    @Inject
    private Reviewer reviewer;

    @Persist
    @Property
    private BeanModel<Article> model;

    @Inject
    private Messages messages;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private CurrentUser currentUser;

    @SetupRender
    void init() {
        model = beanModelSource.createDisplayModel(Article.class, messages);
        model.get("title").label(messages.get("common.Title")).sortable(true);
        model.get("originTitle").label(messages.get("common.Origin")).sortable(true);
        model.get("platformShortName").label(messages.get("common.Platform")).sortable(true);
        model.addEmpty("actions").label(messages.get("common.Actions"));

        if(currentUser.isAnonymous()) {
            model.include("title", "originTitle", "platformShortName");
        } else {
            model.include("title", "originTitle", "platformShortName", "actions");
        }
        if(!thumbnailMode && !gridMode && !tableMode) {
            onActionFromTableMode();
        }
    }

    void onActionFromThumbnailMode() {
        this.thumbnailMode = true;
        this.gridMode = false;
        this.tableMode = false;
    }

    void onActionFromGridMode() {
        this.thumbnailMode = false;
        this.gridMode = true;
        this.tableMode = false;
    }

    void onActionFromTableMode() {
        this.thumbnailMode = false;
        this.gridMode = false;
        this.tableMode = true;
    }

    public String getStars() {
        return reviewer.getStarsOf(article);
    }

    public String getWidth() {
        if(tableMode) {
            return "90";
        }
        return "";
    }

}
