package com.ngdb.entities.article.element;

import com.ngdb.entities.article.Article;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReviewTest {

    private Article article = null;

    @Test
    public void should_convert_percent_in_stars() {
        assertEquals("40", toStars("90%"));

        assertEquals("00", toStars("0%"));
        assertEquals("00", toStars("20%"));
        assertEquals("00", toStars("40%"));
        assertEquals("10", toStars("60%"));
        assertEquals("30", toStars("80%"));
        assertEquals("30", toStars("81%"));
        assertEquals("30", toStars("82%"));
        assertEquals("30", toStars("83%"));
        assertEquals("30", toStars("84%"));
        assertEquals("35", toStars("85%"));
        assertEquals("35", toStars("86%"));
        assertEquals("35", toStars("87%"));
        assertEquals("35", toStars("88%"));
        assertEquals("35", toStars("89%"));
        assertEquals("40", toStars("90%"));
        assertEquals("40", toStars("91%"));
        assertEquals("40", toStars("92%"));
        assertEquals("40", toStars("93%"));
        assertEquals("40", toStars("94%"));
        assertEquals("45", toStars("95%"));
        assertEquals("45", toStars("96%"));
        assertEquals("45", toStars("97%"));
        assertEquals("45", toStars("98%"));
        assertEquals("45", toStars("99%"));
        assertEquals("50", toStars("100%"));
    }

    @Test
    public void should_convert_quotient_in_stars() {
        assertEquals("00", toStars("0/0"));
        assertEquals("10", toStars("1/5"));
        assertEquals("20", toStars("2/5"));
        assertEquals("30", toStars("3/5"));
        assertEquals("40", toStars("4/5"));
        assertEquals("50", toStars("5/5"));

        assertEquals("10", toStars("2/10"));
        assertEquals("25", toStars("5/10"));
        assertEquals("50", toStars("10/10"));
    }

    @Test
    public void should_convert_errors_in_0_star() {
        assertEquals("00", toStars("fdfds"));
    }

    private String toStars(String mark) {
        return new Review("", "", mark, article).getMark();
    }

    @Test
    public void should_order_review_by_mark_then_name() {
        Review review1 = new Review("neogeokult", "http://url", "4/5", null);
        Review review2 = new Review("neogeospirit", "http://url", "4/5", null);

        assertTrue(0 > review1.compareTo(review2));

        Set<Review> reviews = new TreeSet<Review>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(new Review("site1", "http://url", "5/5", null));
        reviews.add(new Review("site2", "http://url", "4/5", null));
        reviews.add(new Review("asite1", "http://url", "4/5", null));

        Review[] sortedReviews = reviews.toArray(new Review[0]);
        assertEquals("site1", sortedReviews[0].getLabel());
        assertEquals("asite1", sortedReviews[1].getLabel());
        assertEquals("neogeokult", sortedReviews[2].getLabel());
        assertEquals("neogeospirit", sortedReviews[3].getLabel());
        assertEquals("site2", sortedReviews[4].getLabel());
    }

    @Test
    public void should_convert_in_percent() {
        assertEquals(100, new Review("neogeokult", "http://url", "5/5", null).getMarkInPercent());
        assertEquals(90, new Review("neogeokult", "http://url", "4/5", null).getMarkInPercent());
        assertEquals(80, new Review("neogeokult", "http://url", "3/5", null).getMarkInPercent());
        assertEquals(70, new Review("neogeokult", "http://url", "2/5", null).getMarkInPercent());
        assertEquals(60, new Review("neogeokult", "http://url", "1/5", null).getMarkInPercent());
        assertEquals(50, new Review("neogeokult", "http://url", "0/5", null).getMarkInPercent());
    }

    @Test
    public void should_convert_in_percent_from_percent() {
        assertEquals(100, new Review("neogeokult", "http://url", "100%", null).getMarkInPercent());
        assertEquals(80, new Review("neogeokult", "http://url", "80%", null).getMarkInPercent());
        assertEquals(60, new Review("neogeokult", "http://url", "60%", null).getMarkInPercent());
        assertEquals(40, new Review("neogeokult", "http://url", "40%", null).getMarkInPercent());
        assertEquals(20, new Review("neogeokult", "http://url", "20%", null).getMarkInPercent());
        assertEquals(0, new Review("neogeokult", "http://url", "0%", null).getMarkInPercent());
    }

}
