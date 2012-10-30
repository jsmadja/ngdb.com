package com.ngdb.entities.article.element;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopItemPictures implements Iterable<Picture> {

    @OneToMany(mappedBy = "shopItem", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Picture> pictures;

    public Picture first() {
        if (pictures == null || pictures.isEmpty()) {
            return Picture.EMPTY;
        }
        return pictures.iterator().next();
    }

    public void add(Picture picture) {
        if (pictures == null) {
            pictures = new HashSet<Picture>();
        }
        pictures.add(picture);
    }

    @Override
    public Iterator<Picture> iterator() {
        return pictures.iterator();
    }

    public void remove(Picture picture) {
        pictures.remove(picture);
    }

    public Set<Picture> all() {
        return Collections.unmodifiableSet(pictures);
    }

    public int getCount() {
        return pictures.size();
    }
}
