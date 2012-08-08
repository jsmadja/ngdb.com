package com.ngdb.entities.user;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Functions.fromWishToArticle;
import static com.ngdb.Predicates.isGameWish;
import static com.ngdb.Predicates.isHardwareWish;
import static javax.persistence.FetchType.LAZY;

import java.util.*;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.Functions;
import com.ngdb.Predicates;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WishList implements Iterable<Wish> {

	@OneToMany(mappedBy = "wisher", fetch = LAZY, orphanRemoval = true)
	private Set<Wish> wishes;

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

	public Set<Wish> getWishes() {
		return Collections.unmodifiableSet(new TreeSet<Wish>(wishes));
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

    public Collection<Wish> getAllHardwares() {
        return filter(wishes, isHardwareWish);
    }
}
