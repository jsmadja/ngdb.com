package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Origin extends AbstractEntity implements Comparable<Origin> {

	@Column(unique = true, nullable = false)
	private String title;

	public Origin() {
	}

	public Origin(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int compareTo(Origin origin) {
		return title.compareTo(origin.title);
	}

}
