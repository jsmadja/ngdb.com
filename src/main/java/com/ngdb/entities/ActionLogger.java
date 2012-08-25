package com.ngdb.entities;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.entities.user.User;

public class ActionLogger {

    @Inject
    private Session session;

    public void addTagAction(User user, Article article) {
        createAction(user, article, "add a tag on");
    }

    public void addCommentAction(User user, Article article) {
        createAction(user, article, "left a comment on");
    }

    public void addReviewAction(User user, Article article) {
        createAction(user, article, "add a review on");
    }

    public void addPropertyAction(User user, Article article) {
        createAction(user, article, "add a property on");
    }

    public void addEditAction(User user, Article article) {
        createAction(user, article, "changed");
    }

    private void createAction(User user, Article article, String message) {
        ArticleAction articleAction = new ArticleAction();
        articleAction.setArticle(article);
        articleAction.setUser(user);
        articleAction.setMessage(message);
        session.merge(articleAction);
    }

    public List<ArticleAction> listLastActions() {
        return session.createCriteria(ArticleAction.class).setMaxResults(15).addOrder(Order.desc("creationDate")).list();
    }

}
