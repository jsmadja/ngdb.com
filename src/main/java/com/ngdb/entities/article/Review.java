package com.ngdb.entities.article;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Review extends AbstractEntity {

	private String label;
	private String url;
	private String mark;

	@ManyToOne
	private Article article;

	public Review() {
	}

	public Review(String label, String url, String mark) {
		super();
		this.label = label;
		this.url = url;
		this.mark = mark;
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
}
