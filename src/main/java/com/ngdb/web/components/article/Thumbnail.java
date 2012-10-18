package com.ngdb.web.components.article;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Thumbnail {

	@Property
	@Parameter
	private Article article;

	@Property
	@Parameter
	private String size;

    @Property
    @Parameter
    private Integer width;

    @Property
	@Parameter
	private boolean noClick;

	@Property
	@Parameter
	private boolean center;

	public String getUrl() {
		if (size == null) {
			return article.getCover().getUrl();
		}
        return article.getCover().getUrl(size);
    }

}
