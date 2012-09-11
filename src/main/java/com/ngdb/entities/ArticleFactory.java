package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import static org.hibernate.criterion.Projections.rowCount;

public class ArticleFactory {

	@Inject
	private Session session;

	public Article findById(Long id) {
		return (Article) session.load(Article.class, id);
	}

    public long getNumArticles() {
        return session.createCriteria(Article.class).setProjection(Projections.property("id")).list().size();
    }
}
