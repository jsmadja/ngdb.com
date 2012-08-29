package com.ngdb.entities.article.element;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Files implements Iterable<File>, Serializable {

    @OrderBy("name")
    @OneToMany(mappedBy = "article")
    private Set<File> files = new LinkedHashSet<File>();

    public Set<File> all() {
        return Collections.unmodifiableSet(files);
    }

    @Override
    public Iterator<File> iterator() {
        return files.iterator();
    }

    public void add(File file) {
        files.add(file);
    }

    public int getCount() {
        return files.size();
    }

}
