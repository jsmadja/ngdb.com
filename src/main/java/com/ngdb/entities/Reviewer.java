package com.ngdb.entities;

import com.ngdb.Mark;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Review;
import com.ngdb.services.Cacher;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.net.CacheRequest;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.ngdb.Mark.ZERO;

public class Reviewer {

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private Session session;

    @Inject
    private Cacher cacher;

    public Set<Review> reviewsOf(Article article) {
        if (article.isGame()) {
            Game game = (Game) article;
            return articleFactory.findAllReviewOfNgh(game.getNgh());
        }
        return article.getReviews().all();
    }

    public Double getAverageMarkOf(Article article) {
        if(cacher.hasAverageMarkOf(article)) {
            return cacher.getAverageMarkOf(article);
        }
        Double averageMark = computeAverageMarkOf(article);
        cacher.setAverageMarkOf(article, averageMark);
        return averageMark;
    }

    private Game game(Game article) {
        return (Game)article;
    }

    private Double computeAverageMarkOf(Article article) {
        Collection<Review> reviews = reviewsOf(article);
        if(!article.isGame()) {
            return 0D;
        }
        if (reviews.isEmpty()) {
            return 0D;
        }
        double sum = 0;
        for (Review review : reviews) {
            sum+= review.getMarkOn5();
        }
        return sum / reviews.size();
    }

    public String getStarsOf(Article article) {
        if(article.isGame()) {
            Game result = (Game) session.load(Game.class, article.getId());
            if (result.getHasReviews()) {
                String mark = result.getAverageMark();
                return new Mark(mark).getStars();
            } else {
                String ngh = result.getNgh();
                List<Game> games = articleFactory.findAllGamesByNgh(ngh);
                for (Game game : games) {
                    if (game.getHasReviews()) {
                        String mark = game.getAverageMark();
                        return new Mark(mark).getStars();
                    }
                }
            }
        }
        return ZERO.getStars();
    }

    public void addReview(Article article, String source, String url, String mark) {
        Review review = new Review(source, url, mark, article);
        session.persist(review);
        article = (Article) session.load(Article.class, article.getId());
        article.addReview(review);
        if(article.isGame()) {
            cacher.invalidateAverageMarkOf((Game)article);
        }
    }

}
