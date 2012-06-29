package com.ngdb.entities.user;

import static javax.persistence.FetchType.LAZY;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

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

}
