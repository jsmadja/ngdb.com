package com.ngdb.entities.article.element;

import java.io.Serializable;
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
public class Notes implements Iterable<Note>, Serializable {

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private Set<Note> notes = new HashSet<Note>();

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
