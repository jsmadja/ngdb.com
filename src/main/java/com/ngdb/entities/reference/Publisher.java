package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
		if (publisher == null || name == null || publisher.name == null) {
			return 0;
		}
		return name.compareToIgnoreCase(publisher.name);
	}

	@Override
	public String toString() {
		return name;
	}

}
