package com.ngdb.entities.article.element;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Reviews implements Iterable<Review> {

	@OneToMany(mappedBy = "article")
	private Set<Review> reviews;

	@Override
	public Iterator<Review> iterator() {
		return reviews.iterator();
	}

}
