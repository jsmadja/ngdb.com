package com.ngdb.entities.article.element;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlAccessorType(FIELD)
@XmlRootElement(name = "picture")
public class Picture {

	public static final Picture EMPTY = new Picture("/ngdb/unknown.png");

	@Column(nullable = false)
	private String url;

	Picture() {
	}

	public Picture(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url == null ? EMPTY.getUrl() : url;
	}

}
