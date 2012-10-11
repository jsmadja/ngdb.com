package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.util.Collection;

import static org.hibernate.criterion.Order.desc;

public class ActionLogger {

    @Inject
    private Session session;

    public void addTagAction(User user, Article article) {
        createAction(user, article, "added_a_new_tag_on");
    }

    public void addCommentAction(User user, Article article) {
        createAction(user, article, "left_a_comment_on");
    }

    public void addReviewAction(User user, Article article) {
        createAction(user, article, "added_a_new_review_on");
    }

    public void addPropertyAction(User user, Article article) {
        createAction(user, article, "added_a_new_property_on");
    }

    public void addFileAction(User user, Article article) {
        createAction(user, article, "added_a_new_file_on");
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
                setMaxResults(12).
                addOrder(desc("creationDate")).
                setCacheable(true);
        return criteria.list();
    }

    public void addArticleInCollectionAction(User user, Article article) {
        createAction(user, article, "added_in_his_collection");
    }

    public void removeArticleFromCollectionAction(User user, Article article) {
        createAction(user, article, "removed_from_his_collection");
    }

    public void addArticleInWishlistAction(User user, Article article) {
        createAction(user, article, "added_in_his_wishlist");
    }

    public void removeArticleFromWishlistAction(User user, Article article) {
        createAction(user, article, "removed_from_his_wishlist");
    }
}
