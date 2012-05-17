package com.ngdb.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Note extends AbstractEntity {

	private String name;

	private String text;

	@ManyToOne
	private Article article;

	public Note() {
	}

	public Note(String name, String text) {
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

}
