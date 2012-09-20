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

@Indexed
@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notes implements Iterable<Note> {

    @IndexedEmbedded
    @OneToMany(mappedBy = "article")
    private Set<Note> notes;

    @Override
    public Iterator<Note> iterator() {
        return notes.iterator();
    }

    public boolean contains(String name) {
        for (Note note : notes) {
            if (note.hasName(name) || note.hasValue(name)) {
                return true;
            }
        }
        return false;
    }

    public void add(Note note) {
        notes.add(note);
    }

    public boolean isEmpty() {
        return notes.isEmpty();
    }

    public Set<Note> all() {
        return Collections.unmodifiableSet(notes);
    }

}
