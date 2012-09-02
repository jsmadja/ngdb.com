package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import static org.hibernate.criterion.Projections.rowCount;

public class ArticleFactory {

	@Inject
	private Session session;

	public Article findById(Long id) {
		return (Article) session.load(Article.class, id);
	}

    public Long getNumArticles() {
        return (Long) session.createCriteria(Article.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }
}
