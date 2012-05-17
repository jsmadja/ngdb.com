package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Picture {

	public static final Picture EMPTY = new Picture("http://cdn1.iconfinder.com/data/icons/realistiK-new/128x128/mimetypes/unknown.png");

	@Column(nullable = false)
	private String url;

	public Picture() {
	}

	public Picture(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
