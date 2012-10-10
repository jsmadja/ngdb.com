package com.ngdb.web.components.article;

import com.ngdb.base.SellItemHistory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.reference.State;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class History {

	@Property
	@Parameter
	private Article article;

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

	public boolean isNotEmpty() {
		return !sellHistory.isEmpty();
	}

}
