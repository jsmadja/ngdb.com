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
public class Reviews implements Iterable<Review> {

	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<Review> reviews;

	@Override
	public Iterator<Review> iterator() {
		return reviews.iterator();
	}

}
