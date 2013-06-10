package com.ngdb.entities.article.element;

import com.ngdb.entities.shop.ShopItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopItemPictures implements Iterable<Picture> {

    @OneToMany(mappedBy = "shopItem", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Picture> pictures;

    @ManyToOne
    private ShopItem shopItem;

    public Picture first() {
        if (pictures == null || pictures.isEmpty()) {
            return shopItem.getMainPicture();
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
