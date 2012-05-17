package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Origin extends AbstractEntity {

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

}
