package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.ngdb.entities.AbstractEntity;

@Entity
public class Genre extends AbstractEntity implements Comparable<Genre> {

	@Column(nullable = false, unique = true)
	private String title;

	public Genre() {

	}

	public Genre(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int compareTo(Genre genre) {
		return title.compareTo(genre.title);
	}

}
