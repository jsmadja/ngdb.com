package com.ngdb.web.services.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceIT {

	@InjectMocks
	PictureService pictureService;

	@Test
	public void should_store_picture() {
		Article article = Mockito.mock(Article.class);
		when(article.getId()).thenReturn(1L);
		Picture picture = pictureService.store("http://88.191.117.240/myimages/logo.png", article);

		assertTrue(picture.getUrl().startsWith("/ngdb/images/articles/1/"));
		assertTrue(picture.getUrl().endsWith(".png"));

		assertTrue(new File(picture.getUrl()).exists());
	}

	@Test
	public void should_store_picture_as_gif() {
		Article article = Mockito.mock(Article.class);
		when(article.getId()).thenReturn(2L);
		Picture picture = pictureService.store("http://dragonspyro93.free.fr/private/DS_AjouterMatiere.gif", article);

		assertTrue(picture.getUrl().startsWith("/ngdb/images/articles/2/"));
		assertTrue(picture.getUrl().endsWith(".gif"));
	}

	@Test
	public void should_return_empty_picture_if_picture_is_unreacheable() {
		Article article = Mockito.mock(Article.class);
		when(article.getId()).thenReturn(2L);
		Picture picture = pictureService.store("http://doesnotexist.comf", article);
		assertEquals(Picture.EMPTY, picture);
	}

}
