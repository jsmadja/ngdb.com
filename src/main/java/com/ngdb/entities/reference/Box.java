package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
		return name.compareToIgnoreCase(box.name);
	}
}
