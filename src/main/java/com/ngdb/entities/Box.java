package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Box extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	public Box() {
	}

	public Box(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
