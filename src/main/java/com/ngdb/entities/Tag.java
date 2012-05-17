package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Tag extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	public Tag() {
	}

	public Tag(String name) {
		this.name = name;
	}

}
