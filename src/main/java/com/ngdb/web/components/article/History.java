package com.ngdb.web.components.article;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Museum;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.reference.State;
import com.ngdb.web.pages.article.SellItemHistory;

public class History {

	@Property
	@Parameter
	private Article article;

	@Inject
	private Museum museum;

	@Inject
	private WishBox wishBox;

	@Property
	private Collection<SellItemHistory> sellHistory;

	@Property
	private SellItemHistory sellItemHistory;

	@Inject
	private ReferenceService referenceService;

	@SetupRender
	public void init() {
		List<State> states = referenceService.findAllStates();
		for (State state : states) {
			if (article.hasShopItemInState(state)) {
				sellHistory.add(new SellItemHistory(article, state));
			}
		}
	}

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
