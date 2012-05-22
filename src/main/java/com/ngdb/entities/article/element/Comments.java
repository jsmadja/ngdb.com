package com.ngdb.entities.article.element;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Comments implements Iterable<Comment> {

	@OneToMany(mappedBy = "article")
	private Set<Comment> comments;

	public void add(Comment comment) {
		this.comments.add(comment);
	}

	@Override
	public Iterator<Comment> iterator() {
		return comments.iterator();
	}

}
