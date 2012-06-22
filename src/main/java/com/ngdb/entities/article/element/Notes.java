package com.ngdb.entities.article.element;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notes implements Iterable<Note> {

	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<Note> notes;

	@Override
	public Iterator<Note> iterator() {
		return notes.iterator();
	}

}
