package com.ngdb.web.components.article;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Museum;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;

public class History {

	@Property
	@Parameter
	private Article article;

	@Inject
	private Museum museum;

	@Inject
	private WishBox wishBox;

	public String getCollectionRank() {
		return museum.getRankOf(article);
	}

	public String getWishRank() {
		return wishBox.getRankOf(article);
	}

	public int getNumAvailableCopy() {
		return article.getAvailableCopyCount();
	}
}
