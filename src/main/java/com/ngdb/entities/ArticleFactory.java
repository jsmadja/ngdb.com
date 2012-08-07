package com.ngdb.entities;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;

import java.util.List;

public class ArticleFactory {

	@Inject
	private Session session;

	public Article findById(Long id) {
		return (Article) session.load(Article.class, id);
	}

    public List<Article> findAll() {
        return session.createCriteria(Article.class).list();
    }
}
