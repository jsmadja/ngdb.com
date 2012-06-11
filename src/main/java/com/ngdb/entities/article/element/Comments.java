package com.ngdb.entities.article.element;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Embeddable
public class Comments implements Iterable<Comment> {

	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<Comment> comments;

	@Override
	public Iterator<Comment> iterator() {
		return comments.iterator();
	}

	public boolean isEmpty() {
		return comments == null || comments.isEmpty();
	}

}
