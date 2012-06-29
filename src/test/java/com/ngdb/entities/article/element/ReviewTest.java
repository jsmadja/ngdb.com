package com.ngdb.entities.article.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ngdb.entities.article.Article;

public class ReviewTest {

	private Article article = null;

	@Test
	public void should_convert_percent_in_stars() {
		assertEquals("00", toStars("0%"));
		assertEquals("10", toStars("20%"));
		assertEquals("20", toStars("40%"));
		assertEquals("30", toStars("60%"));
		assertEquals("40", toStars("80%"));
		assertEquals("50", toStars("90%"));
		assertEquals("50", toStars("91%"));
		assertEquals("50", toStars("92%"));
		assertEquals("50", toStars("93%"));
		assertEquals("50", toStars("94%"));
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
	}

	@Test
	public void should_convert_errors_in_0_star() {
		assertEquals("00", toStars("fdfds"));
	}

	private String toStars(String mark) {
		return new Review("", "", mark, article).getMark();
	}

}
