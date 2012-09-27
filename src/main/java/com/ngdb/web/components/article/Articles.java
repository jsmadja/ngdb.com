package com.ngdb.web.components.article;

import com.ngdb.StarsUtil;
import com.ngdb.entities.ArticleFactory;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import com.ngdb.entities.article.Article;

import java.util.Collection;

public class Articles {

    @Property
    @Parameter(allowNull=false)
    private Collection<Article> articles;

    @Property
    private Article article;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private Session session;

    @Persist
    @Property
    private boolean thumbnailMode;

    @Persist
    @Property
    private boolean gridMode;

    @Persist
    @Property
    private boolean tableMode;

    @SetupRender
    void init() {
        if(!thumbnailMode && !gridMode && !tableMode) {
            onActionFromTableMode();
        }
    }

    void onActionFromThumbnailMode() {
        this.thumbnailMode = true;
        this.gridMode = false;
        this.tableMode = false;
    }

    void onActionFromGridMode() {
        this.thumbnailMode = false;
        this.gridMode = true;
        this.tableMode = false;
    }

    void onActionFromTableMode() {
        this.thumbnailMode = false;
        this.gridMode = false;
        this.tableMode = true;
    }

    public String getStars() {
        return StarsUtil.getStars(article, session, articleFactory);
    }

}
