package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Platform extends AbstractEntity implements Comparable<Platform> {

	@Column(nullable = false, unique = true)
	private String name;

	public Platform() {
	}

	public Platform(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Platform platform) {
		return name.compareTo(platform.name);
	}

}
