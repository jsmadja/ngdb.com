package com.ngdb.entities.article.element;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Notes implements Iterable<Note> {

	@OneToMany(mappedBy = "article")
	private Set<Note> notes;

	public void add(Note note) {
		if (notes == null) {
			notes = new HashSet<Note>();
		}
		this.notes.add(note);
	}

	@Override
	public Iterator<Note> iterator() {
		return notes.iterator();
	}

}
