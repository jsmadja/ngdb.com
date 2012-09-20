package com.ngdb.entities.article.element;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comments implements Iterable<Comment> {

    @OneToMany(mappedBy = "article")
    private Set<Comment> comments;

    @Override
    public Iterator<Comment> iterator() {
        return comments.iterator();
    }

    public boolean isEmpty() {
        return comments == null || comments.isEmpty();
    }

    public Set<Comment> all() {
        return Collections.unmodifiableSet(comments);
    }

}
