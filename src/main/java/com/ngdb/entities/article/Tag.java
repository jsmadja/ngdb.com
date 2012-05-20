package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

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
