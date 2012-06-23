package com.ngdb.web.components.article;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.ArticlePictures;
import com.ngdb.entities.article.element.Picture;

public class Gallery {

	@Property
	@Parameter(allowNull = false)
	private Article article;

	@Property
	private Picture picture;

	public ArticlePictures getPictures() {
		return article.getPictures();
	}

}
