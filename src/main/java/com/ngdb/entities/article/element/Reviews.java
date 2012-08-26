package com.ngdb.entities.article.element;

import java.util.Collections;
import java.util.HashSet;
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
    private Set<Review> reviews = new HashSet<Review>();

    @Override
    public Iterator<Review> iterator() {
        return reviews.iterator();
    }

    public Set<Review> all() {
        return Collections.unmodifiableSet(reviews);
    }

    public void add(Review review) {
        this.reviews.add(review);
    }

    public int count() {
        return reviews.size();
    }

}
