package com.ngdb.web.components.article;

import com.ngdb.StarsUtil;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.*;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class ReviewBlock {

    @Property
    @Parameter
    private Article article;

    @Property
    private Review review;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private ArticleFactory articleFactory;

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

    void onActivate() {
        suggestions.addAll(registry.findAllTags());
    }

    @SetupRender
    void init() {
        reviews = loadReviews();
    }

    private Set<Review> loadReviews() {
        if (article.isGame()) {
            Game game = (Game) article;
            Set<Review> reviews = new TreeSet<Review>(game.getReviews().all());
            List<Game> relatedGames = articleFactory.findAllGamesByNgh(game.getNgh());
            for (Game relatedGame : relatedGames) {
                reviews.addAll(relatedGame.getReviews().all());
            }
            return reviews;
        }
        return article.getReviews().all();
    }

    public double getAverageMarkAsDouble() {
        if(!article.isGame()) {
            return 0;
        }
        if (reviews.isEmpty()) {
            return 0;
        }
        double sum = 0;
        for (Review review : reviews) {
            int markInPercent = review.getMarkInPercent();
            markInPercent -= 50;
            if(markInPercent > 0) {
                sum += (markInPercent/10);
            }
        }
        return sum / reviews.size();
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
        String mark = review.getMark();
        return StarsUtil.toStarsHtml(mark);
    }

}
