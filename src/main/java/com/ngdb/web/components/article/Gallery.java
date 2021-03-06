package com.ngdb.web.components.article;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.ArticlePictures;
import com.ngdb.entities.article.element.Picture;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class Gallery {

	@Property
	@Parameter(allowNull = false)
	private Article article;

	@Property
	private Picture picture;

	public JSONObject getParams() {
		return new JSONObject("maxHeight", "600px");
	}

	public Set<Picture> getPictures() {
        Set<Picture> pictures = new HashSet<Picture>(article.getPictures().all());
        pictures.remove(article.getCover());
        return pictures;
	}

	public String getPictureUrl() {
		return picture.getUrl("high");
	}

}
