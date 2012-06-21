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
		if (name == null || platform.name == null) {
			return 0;
		}
		return name.compareToIgnoreCase(platform.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
