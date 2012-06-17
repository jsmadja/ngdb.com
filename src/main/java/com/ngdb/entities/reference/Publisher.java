package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Publisher extends AbstractEntity implements Comparable<Publisher> {

	@Column(nullable = false, unique = true)
	private String name;

	public Publisher() {
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Publisher publisher) {
		if (publisher == null) {
			return 0;
		}
		return name.compareTo(publisher.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
