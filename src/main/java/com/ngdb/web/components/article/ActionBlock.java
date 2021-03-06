package com.ngdb.web.components.article;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

public class ActionBlock {

    @Inject
    private CurrentUser currentUser;

    @Property
    @Parameter(required = false)
    private boolean asButton;

    @Property
    @Parameter
    private Article article;

    @Inject
    private ArticleFactory articleFactory;

    @Property
    @Parameter
    private String returnPage;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone actionBlockZone;

    public boolean isAddableToCollection() {
        return currentUser.canAddToCollection(article);
    }

    public boolean isRemoveableFromCollection() {
        return currentUser.canRemoveFromCollection(article);
    }

    public User getUser() {
        return currentUser.getUser();
    }

    @CommitAfter
    void onActionFromAddToCollection(Article article) {
        this.article = article;
        currentUser.addToCollection(article);
        ajaxResponseRenderer.addRender(getZoneId(), actionBlockZone.getBody());
    }

    @CommitAfter
    void onActionFromRemoveCollection(Article article) {
        this.article = article;
        currentUser.removeFromCollection(article);
        ajaxResponseRenderer.addRender(actionBlockZone);
    }

    @CommitAfter
    Object onActionFromRemoveCollectionLink(Article article) {
        currentUser.removeFromCollection(article);
        return returnPage;
    }

    @CommitAfter
    Object onActionFromCollectionLink(Article article) {
        currentUser.addToCollection(article);
        return returnPage;
    }

    public String getZoneId() {
        return "actionBlockZone_" + article.getId().toString();
    }

    public String getByArticle() {
        return "byArticle";
    }

}
