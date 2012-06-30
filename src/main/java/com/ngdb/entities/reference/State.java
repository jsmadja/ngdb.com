package com.ngdb.entities.reference;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
		return getId().compareTo(state.getId());
	}

	@Override
	public String toString() {
		return title;
	}
}
