package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Box extends AbstractEntity implements Comparable<Box> {

	@Column(nullable = false, unique = true)
	private String name;

	public Box() {
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Box box) {
		return name.compareTo(box.name);
	}
}
