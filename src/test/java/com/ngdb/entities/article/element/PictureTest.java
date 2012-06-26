package com.ngdb.entities.article.element;

import org.junit.Assert;
import org.junit.Test;

public class PictureTest {

	@Test
	public void should_translated_in_watermarked_url() {
		Picture picture = new Picture("02590a2-e5bf-4637-a42b-3deab74e2ed0.jpg");
		Assert.assertEquals("02590a2-e5bf-4637-a42b-3deab74e2ed0_wm.jpg", picture.toWatermarkedUrl());
	}

}
