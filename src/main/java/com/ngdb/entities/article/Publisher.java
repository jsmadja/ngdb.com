package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Publisher extends AbstractEntity implements Comparable<Publisher> {

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

	@Override
	public int compareTo(Publisher publisher) {
		return name.compareTo(publisher.name);
	}

}
