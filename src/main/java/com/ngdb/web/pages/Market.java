package com.ngdb.web.pages;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.MarketFilter;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;

public class Market {

	@Inject
	private com.ngdb.entities.Market market;

	@Property
	private String username;

	@Property
	private ShopItem shopItem;

	@Inject
	private ReferenceService referenceService;

	@Persist
	private MarketFilter marketFilter;

	@Property
	private Platform platform;

	@Property
	private Origin origin;

	void onActivate() {
		if (marketFilter == null) {
			marketFilter = new MarketFilter(market);
		}
	}

	public Collection<ShopItem> getShopItems() {
		return marketFilter.getShopItems();
	}

	Object onActionFromClearFilters() {
		marketFilter.clear();
		return this;
	}

	Object onActionFromSelectHardwares() {
		marketFilter.filterByHardwares();
		return this;
	}

	Object onActionFromSelectGames() {
		marketFilter.filterByGames();
		return this;
	}

	public long getNumGames() {
		return marketFilter.getNumGames();
	}

	public long getNumHardwares() {
		return marketFilter.getNumHardwares();
	}

	public List<Platform> getPlatforms() {
		return referenceService.getPlatforms();
	}

	public boolean isArticleInThisPlatform() {
		return getNumArticlesInThisPlatform() > 0;
	}

	public boolean isFilteredByThisPlatform() {
		if (platform == null) {
			return false;
		}
		return marketFilter.isFilteredBy(platform);
	}

	Object onActionFromFilterPlatform(Platform platform) {
		marketFilter.filterByPlatform(platform);
		return this;
	}

	public int getNumArticlesInThisPlatform() {
		return marketFilter.getNumShopItemsInThisPlatform(platform);
	}

	public List<Origin> getOrigins() {
		return referenceService.getOrigins();
	}

	public boolean isFilteredByThisOrigin() {
		return marketFilter.isFilteredBy(origin);
	}

	public boolean isArticleInThisOrigin() {
		if (origin == null) {
			return false;
		}
		return getNumArticlesInThisOrigin() > 0;
	}

	Object onActionFromFilterOrigin(Origin origin) {
		marketFilter.filterByOrigin(origin);
		return this;
	}

	public int getNumArticlesInThisOrigin() {
		return marketFilter.getNumShopItemsInThisOrigin(origin);
	}

	public User getUser() {
		return marketFilter.getFilteredUser();
	}

	public boolean isFilteredByGames() {
		return marketFilter.isFilteredByGames();
	}

	public String getQueryLabel() {
		return marketFilter.getQueryLabel();
	}

	public String getViewPage() {
		return "/shopitem/ShopItemView";
	}

	public int getNumResults() {
		return getShopItems().size();
	}

}
