package com.ngdb.entities.user;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Predicates.*;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WishList implements Iterable<Wish> {

    @OneToMany(mappedBy = "wisher", orphanRemoval = true)
    private List<Wish> wishes;

    boolean contains(Article article) {
        for (Wish wish : wishes) {
            Long searchId = article.getId();
            Long idInWishList = wish.getArticle().getId();
            if (searchId.equals(idInWishList)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Wish> iterator() {
        return wishes.iterator();
    }

    void addInWishList(Wish wish) {
        wishes.add(wish);
    }

    public int getNumWishes() {
        return wishes.size();
    }

    public void removeFromWishList(Article article) {
        if (contains(article)) {
            Wish wishToRemove = null;
            for (Wish wish : wishes) {
                if (wish.getArticle().getId().equals(article.getId())) {
                    wishToRemove = wish;
                    break;
                }
            }
            wishes.remove(wishToRemove);
        }
    }

    public Collection<Wish> getAllGames() {
        return filter(wishes, isGameWish);
    }

    public int getNumWishedHardwares() {
        return getAllHardwares().size();
    }

    public int getNumWishedGames() {
        return getAllGames().size();
    }

    public long getNumWishedAccessories() {
        return getAllAccessories().size();
    }

    public Collection<Wish> getAllHardwares() {
        return filter(wishes, isHardwareWish);
    }

    public Collection<Wish> getAllAccessories() {
        return filter(wishes, isAccessoryWish);
    }

}
