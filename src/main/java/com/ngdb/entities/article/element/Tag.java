package com.ngdb.entities.article.element;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Tag extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	Tag() {
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
