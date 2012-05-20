package com.ngdb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class State extends AbstractEntity implements Comparable<State> {

	@Column(unique = true, nullable = false)
	private String title;

	public State() {
	}

	public State(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int compareTo(State state) {
		return title.compareTo(state.title);
	}
}
