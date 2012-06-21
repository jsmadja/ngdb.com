package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class State extends AbstractEntity implements Comparable<State> {

	@Column(unique = true, nullable = false)
	private String title;

	public State() {
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int compareTo(State state) {
		return title.compareToIgnoreCase(state.title);
	}

	@Override
	public String toString() {
		return title;
	}
}
