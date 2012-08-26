package com.ngdb.entities.article;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ngdb.entities.article.element.Review;

public class ArticleTest {

    @Test
    public void should_compute_average() {

        Game game = new Game();
        game.addReview(new Review("neo", "http:", "3/5", game));
        String averageMark = game.getAverageMark();
        assertEquals("30", averageMark);

    }

}
