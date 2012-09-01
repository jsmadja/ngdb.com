package com.ngdb.entities.article.element;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reviews implements Iterable<Review> {

    @OneToMany(mappedBy = "article")
    private Set<Review> reviews;

    @Override
    public Iterator<Review> iterator() {
        return reviews.iterator();
    }

    public Set<Review> all() {
        return Collections.unmodifiableSet(reviews);
    }

    public void add(Review review) {
        if(reviews == null) {
            reviews = new TreeSet<Review>();
        }
        this.reviews.add(review);
    }

    public int count() {
        return reviews.size();
    }

}
