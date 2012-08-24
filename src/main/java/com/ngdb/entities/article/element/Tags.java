package com.ngdb.entities.article.element;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tags implements Iterable<Tag> {

    @OrderBy("name")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ArticleTags", inverseJoinColumns = { @JoinColumn(name = "tag_id") }, joinColumns = { @JoinColumn(name = "article_id") })
    private Set<Tag> tags = new TreeSet<Tag>();

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

    public void add(Tag tag) {
        tags.add(tag);
    }

    public boolean contains(Tag tag) {
        return tags.contains(tag);
    }

}
