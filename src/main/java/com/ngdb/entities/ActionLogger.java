package com.ngdb.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.entities.user.User;

import static org.hibernate.criterion.Order.desc;

public class ActionLogger {

    @Inject
    private Session session;

    public void addTagAction(User user, Article article) {
        createAction(user, article, "added a new tag on");
    }

    public void addCommentAction(User user, Article article) {
        createAction(user, article, "left a comment on");
    }

    public void addReviewAction(User user, Article article) {
        createAction(user, article, "added a new review on");
    }

    public void addPropertyAction(User user, Article article) {
        createAction(user, article, "added a new property on");
    }

    public void addFileAction(User user, Article article) {
        createAction(user, article, "added a new file on");
    }

    public void addPictureAction(User user, Article article) {
        createAction(user, article, "added a new picture on");
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

    public Collection<ArticleAction> listLastActions() {
        Criteria criteria = session.createCriteria(ArticleAction.class).
                setMaxResults(15).
                addOrder(desc("creationDate")).
                setCacheable(true);
        return criteria.list();
    }

}
