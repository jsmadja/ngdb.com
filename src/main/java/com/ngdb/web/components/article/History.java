package com.ngdb.web.components.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.base.SellItemHistory;
import com.ngdb.entities.Museum;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.reference.State;

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
		sellHistory = new ArrayList<SellItemHistory>();
		for (State state : states) {
			if (article.hasShopItemInState(state)) {
				sellHistory.add(new SellItemHistory(article, state));
			}
		}
	}

	public String getCollectionRank() {
		return asRankString(museum.getRankOf(article));
	}

	public String getWishRank() {
		return asRankString(wishBox.getRankOf(article));
	}

	public int getNumAvailableCopy() {
		return article.getAvailableCopyCount();
	}

	private String asRankString(int value) {
		if (value == Integer.MAX_VALUE) {
			return "N/A";
		}
		int hundredRemainder = value % 100;
		int tenRemainder = value % 10;
		if (hundredRemainder - tenRemainder == 10) {
			return value + "th";
		}
		switch (tenRemainder) {
		case 1:
			return value + "st";
		case 2:
			return value + "nd";
		case 3:
			return value + "rd";
		default:
			return value + "th";
		}
	}

	public boolean isNotEmpty() {
		return !sellHistory.isEmpty();
	}

	public boolean isBuyable() {
		return getNumAvailableCopy() > 0;
	}

}
