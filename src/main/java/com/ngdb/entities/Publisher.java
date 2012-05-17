package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Publisher extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	public Publisher() {
	}

	public Publisher(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
