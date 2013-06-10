package com.ngdb.web.components.article;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.services.Cacher;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Thumbnail {

	@Property
	@Parameter
	private Article article;

    @Property
    @Parameter
    private String styleClass;

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
    private PictureService pictureService;

    @Inject
    private Cacher cacher;

	public String getUrl() {
        Picture cover = pictureService.getCoverOf(article);
        if (size == null) {
			return cover.getUrl();
		}
        return cover.getUrl(size);
    }

}
