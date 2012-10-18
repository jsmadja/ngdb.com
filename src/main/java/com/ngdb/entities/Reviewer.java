package com.ngdb.entities;

import com.ngdb.Mark;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Review;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

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

    public Set<Review> reviewsOf(Article article) {
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

    public double getAverageMarkOf(Article article) {
        Collection<Review> reviews = reviewsOf(article);
        if(!article.isGame()) {
            return 0;
        }
        if (reviews.isEmpty()) {
            return 0;
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

}