package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Picture {

	public static final Picture EMPTY = new Picture("/ngdb/unknown.png");

	@Column(nullable = false)
	private String url;

	public Picture() {
	}

	public Picture(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url == null ? EMPTY.getUrl() : url;
	}

}
