package com.ngdb.entities.user;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;

@Embeddable
public class WishList {

	@OneToMany(mappedBy = "wisher")
	private Set<Wish> wishes;

	public boolean contains(Article article) {
		for (Wish wish : wishes) {
			if (article.equals(wish.getArticle())) {
				return false;
			}
		}
		return true;
	}

}
