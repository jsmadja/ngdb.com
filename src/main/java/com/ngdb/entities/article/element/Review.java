package com.ngdb.entities.article.element;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review extends AbstractEntity {

	private String label;
	private String url;
	private String mark;

	@ManyToOne
	private Article article;

	Review() {
	}

	public String getLabel() {
		return label;
	}

	public String getMark() {
		return mark;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return label + " " + url + " " + mark;
	}
}
