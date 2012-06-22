package com.ngdb.entities.article.element;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note extends AbstractEntity {

	private String name;

	private String text;

	@ManyToOne
	private Article article;

	Note() {
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
