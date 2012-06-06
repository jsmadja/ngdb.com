package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Platform extends AbstractEntity implements Comparable<Platform> {

	@Column(nullable = false, unique = true)
	private String name;

	public Platform() {
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Platform platform) {
		return name.compareTo(platform.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
