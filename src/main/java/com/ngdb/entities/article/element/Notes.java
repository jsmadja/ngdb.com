package com.ngdb.entities.article.element;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Notes implements Iterable<Note> {

	@OneToMany(mappedBy = "article")
	private Set<Note> notes;

	@Override
	public Iterator<Note> iterator() {
		return notes.iterator();
	}

}
