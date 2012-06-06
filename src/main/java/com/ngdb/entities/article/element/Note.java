package com.ngdb.entities.article.element;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
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
