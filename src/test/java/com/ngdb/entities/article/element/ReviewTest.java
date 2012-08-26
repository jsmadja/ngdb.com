package com.ngdb.entities.article.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import com.ngdb.entities.article.Article;

public class ReviewTest {

	private Article article = null;

	@Test
	public void should_convert_percent_in_stars() {
        assertEquals("45", toStars("90%"));

        assertEquals("00", toStars("0%"));
		assertEquals("10", toStars("20%"));
		assertEquals("20", toStars("40%"));
		assertEquals("30", toStars("60%"));
		assertEquals("40", toStars("80%"));
		assertEquals("45", toStars("91%"));
		assertEquals("45", toStars("92%"));
		assertEquals("45", toStars("93%"));
		assertEquals("45", toStars("94%"));
		assertEquals("50", toStars("95%"));
		assertEquals("50", toStars("96%"));
		assertEquals("50", toStars("97%"));
		assertEquals("50", toStars("98%"));
		assertEquals("50", toStars("99%"));
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
		assertEquals("15", toStars("2.5/10"));
		assertEquals("50", toStars("10/10"));

        assertEquals("45", toStars("9/10"));
        assertEquals("15", toStars("1/3"));

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

}
