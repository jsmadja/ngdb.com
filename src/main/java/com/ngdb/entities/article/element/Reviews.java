package com.ngdb.entities.article.element;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

@Indexed
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reviews implements Iterable<Review> {

    @IndexedEmbedded
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
