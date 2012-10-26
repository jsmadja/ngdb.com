package com.ngdb.web.components.article;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.services.Cacher;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

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

    @Inject
    private Cacher cacher;

	public String getUrl() {
        Picture cover;
        if(cacher.hasCoverOf(article)) {
            cover = cacher.getCoverOf(article);
        } else {
            cover = article.getCover();
            cacher.setCoverOf(article, cover);
        }
        if (size == null) {
			return cover.getUrl();
		}
        return cover.getUrl(size);
    }

}
