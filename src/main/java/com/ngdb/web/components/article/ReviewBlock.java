package com.ngdb.web.components.article;

import com.ngdb.entities.Registry;
import com.ngdb.entities.Reviewer;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class ReviewBlock {

    @Property
    @Parameter
    private Article article;

    @Property
    private Review review;

    @Inject
    private CurrentUser currentUser;

    private Collection<String> suggestions = new TreeSet<String>();

    @Property
    private String label;

    @Property
    private String url;

    @Property
    private String mark;

    @Inject
    private Registry registry;

    @Property
    private Set<Review> reviews;

    @Inject
    private Reviewer reviewer;

    void onActivate() {
        suggestions.addAll(registry.findAllTags());
    }

    @SetupRender
    void init() {
        reviews = reviewer.reviewsOf(article);
    }

    public double getAverageMarkAsDouble() {
        return reviewer.getAverageMarkOf(article);
    }

    public int getReviewsCount() {
        return reviews.size();
    }

    @CommitAfter
    public void onSuccess() {
        if (isNotBlank(label) && isNotBlank(url) && isNotBlank(mark)) {
            article.updateModificationDate();
            currentUser.addReviewOn(article, label, url, mark);
        }
    }

    public User getUser() {
        return currentUser.getUser();
    }

    public String getStars() {
        return review.getMarkAsStars();
    }

}
