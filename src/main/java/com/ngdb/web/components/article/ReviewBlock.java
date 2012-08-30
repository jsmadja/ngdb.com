package com.ngdb.web.components.article;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.StarsUtil;
import com.ngdb.entities.GameFactory;
import com.ngdb.entities.Registry;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class ReviewBlock {

    @Property
    @Parameter
    private Article article;

    @Property
    private Review review;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private GameFactory gameFactory;

    private Collection<String> suggestions = new TreeSet<String>();

    @Property
    private String label;

    @Property
    private String url;

    @Property
    private String mark;

    @Inject
    private Registry registry;

    void onActivate() {
        suggestions.addAll(registry.findAllTags());
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

    public Set<Review> getReviews() {
        if (article.isGame()) {
            Game game = (Game) article;
            Set<Review> reviews = new TreeSet<Review>(game.getReviews().all());
            List<Game> relatedGames = gameFactory.findAllByNgh(game.getNgh());
            for (Game relatedGame : relatedGames) {
                reviews.addAll(relatedGame.getReviews().all());
            }
            return reviews;
        }
        return article.getReviews().all();
    }

    public String getStars() {
        String mark = review.getMark();
        return StarsUtil.toStarsHtml(mark);
    }

}
