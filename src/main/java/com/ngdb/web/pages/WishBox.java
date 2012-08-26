package com.ngdb.web.pages;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.entities.WishBoxFilter;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.pages.base.Redirections;

public class WishBox {

	@Property
	private Wish wish;

	@Inject
	private com.ngdb.entities.WishBox wishBox;

	@Inject
	private Population population;

    @Persist
    private WishBoxFilter wishBoxFilter;

    @Property
    private Platform platform;

    @Property
    private Origin origin;

    @Inject
    private ReferenceService referenceService;

    void onActivate() {
        if (wishBoxFilter == null) {
            wishBoxFilter = new WishBoxFilter(wishBox);
        }
    }

    boolean onActivate(String filterName, String value) {
        if (value == null) {
            onActivate();
            return true;
        }
        wishBoxFilter = new WishBoxFilter(wishBox);
        Filter filter = Filter.valueOf(Filter.class, filterName);
        switch (filter) {
            case byUser:
                User user = population.findById(Long.valueOf(value));
                wishBoxFilter.filterByUser(user);
                break;
        }
        return true;
    }

    public Collection<Wish> getWishes() {
        return wishBoxFilter.getWishes();
    }

	public String getViewPage() {
		return Redirections.toViewPage(wish.getArticle());
	}

	public String getMainPictureUrl() {
		return wish.getArticle().getMainPicture().getUrl("small");
	}

    Object onActionFromClearFilters() {
        wishBoxFilter.clear();
        return this;
    }

    Object onActionFromSelectHardwares() {
        wishBoxFilter.filterByHardwares();
        return this;
    }

    Object onActionFromSelectGames() {
        wishBoxFilter.filterByGames();
        return this;
    }

    public long getNumGames() {
        return wishBoxFilter.getNumGames();
    }

    public long getNumHardwares() {
        return wishBoxFilter.getNumHardwares();
    }

    public List<Platform> getPlatforms() {
        return referenceService.getPlatforms();
    }

    public boolean isWishInThisPlatform() {
        return getNumWishesInThisPlatform() > 0;
    }

    public boolean isFilteredByThisPlatform() {
        if (platform == null) {
            return false;
        }
        return wishBoxFilter.isFilteredBy(platform);
    }

    Object onActionFromFilterPlatform(Platform platform) {
        wishBoxFilter.filterByPlatform(platform);
        return this;
    }

    public int getNumWishesInThisPlatform() {
        return wishBoxFilter.getNumWishesInThisPlatform(platform);
    }

    public List<Origin> getOrigins() {
        return referenceService.getOrigins();
    }

    public boolean isFilteredByThisOrigin() {
        return wishBoxFilter.isFilteredBy(origin);
    }

    public boolean isWishInThisOrigin() {
        if (origin == null) {
            return false;
        }
        return getNumWishesInThisOrigin() > 0;
    }

    Object onActionFromFilterOrigin(Origin origin) {
        wishBoxFilter.filterByOrigin(origin);
        return this;
    }

    public int getNumWishesInThisOrigin() {
        return wishBoxFilter.getNumWishesInThisOrigin(origin);
    }

    public User getUser() {
        return wishBoxFilter.getFilteredUser();
    }

    public boolean isFilteredByGames() {
        return wishBoxFilter.isFilteredByGames();
    }

    public String getQueryLabel() {
        return wishBoxFilter.getQueryLabel();
    }

    public int getNumResults() {
        return getWishes().size();
    }
}
