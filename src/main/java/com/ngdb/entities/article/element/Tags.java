package com.ngdb.entities.article.element;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@Indexed
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tags implements Iterable<Tag> {

    @OrderBy("name")
    @IndexedEmbedded
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Tag> tags;

    public Set<Tag> all() {
        return Collections.unmodifiableSet(tags);
    }

    @Override
    public Iterator<Tag> iterator() {
        return tags.iterator();
    }

    public boolean contains(String tagName) {
        for (Tag tag : tags) {
            if (tag.hasName(tagName)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Tag _tag) {
        for (Tag tag : tags) {
            if (tag.hasName(_tag.getName())) {
                return true;
            }
        }
        return false;
    }

    public void add(Tag tag) {
        tags.add(tag);
    }

    public int count() {
        return tags.size();
    }

    @Override
    public String toString() {
        if (tags == null) {
            return "";
        }
        return Arrays.toString(tags.toArray());
    }

    public boolean isEqualTo(Tags currentTags) {
        if(count() != currentTags.count()) {
            return false;
        }
        for (Tag currentTag : currentTags) {
            if(!contains(currentTag)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return tags.isEmpty();
    }
}
