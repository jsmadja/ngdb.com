package com.ngdb.entities.article.element;

import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticlePictures implements Iterable<Picture> {

    @ElementCollection
    @OrderBy("creationDate")
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Picture> pictures;

    @ManyToOne
    private Article article;

    public Picture first() {
        if (pictures == null || pictures.isEmpty()) {
            return article.getCover();
        }
        return pictures.iterator().next();
    }

    public void add(Picture picture) {
        pictures.add(picture);
    }

    @Override
    public Iterator<Picture> iterator() {
        return pictures.iterator();
    }

    public Set<Picture> all() {
        return Collections.unmodifiableSet(pictures);
    }

    public void remove(Picture picture) {
        pictures.remove(picture);
    }

    public int count() {
        return pictures.size();
    }
}
