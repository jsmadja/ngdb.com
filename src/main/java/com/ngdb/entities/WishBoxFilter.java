package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.WishPredicates;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;

public class WishBoxFilter {

    private WishBox wishBox;

    private boolean filteredByGames;

    private Origin filteredOrigin;

    private Platform filteredPlatform;

    private User filteredUser;

    private boolean consolidated;

    public WishBoxFilter(WishBox wishBox) {
        this.wishBox = wishBox;
        clear();
    }

    public void clear() {
        this.filteredOrigin = null;
        this.filteredPlatform = null;
        this.filteredUser = null;
        this.consolidated = false;
        this.filteredByGames = true;
    }

    public String getQueryLabel() {
        String queryLabel = "all ";
        if (filteredByGames) {
            queryLabel += orange("games");
        } else {
            queryLabel += orange("hardwares");
        }
        if (filteredOrigin != null) {
            queryLabel += " from " + orange(filteredOrigin.getTitle());
        }
        if (filteredPlatform != null) {
            queryLabel += " on " + orange(filteredPlatform.getName());
        }
        if (filteredUser != null) {
            queryLabel += " wished by " + orange(filteredUser.getLogin());
        }
        return queryLabel;
    }

    private String orange(String name) {
        return "<span class=\"orange\">" + name + "</span>";
    }

    public void filterByUser(User user) {
        this.filteredUser = user;
    }

    public void filterByOrigin(Origin origin) {
        this.filteredOrigin = origin;
    }

    public void filterByPlatform(Platform platform) {
        this.filteredPlatform = platform;
    }

    private Collection<Wish> applyFilters(List<Predicate<Wish>> filters) {
        Collection<Wish> filteredWishes = newInitialWishesList();
        buildFilters(filters);
        for (Predicate<Wish> filter : filters) {
            filteredWishes = filter(filteredWishes, filter);
        }
        return filteredWishes;
    }

    private void buildFilters(List<Predicate<Wish>> filters) {
        if (filteredOrigin != null) {
            filters.add(new WishPredicates.OriginPredicate(filteredOrigin));
        }
        if (filteredPlatform != null) {
            filters.add(new WishPredicates.PlatformPredicate(filteredPlatform));
        }
    }

    public List<Wish> getWishes() {
        List<Predicate<Wish>> filters = Lists.newArrayList();
        Collection<Wish> filteredArticle = applyFilters(filters);
        List<Wish> wishes = new ArrayList<Wish>(filteredArticle);
        Collections.sort(wishes);
        return wishes;
    }

    public void filterByGames() {
        this.filteredByGames = true;
    }

    public void filterByHardwares() {
        this.filteredByGames = false;
    }

    public boolean isFilteredBy(Platform platform) {
        if (filteredPlatform == null) {
            return false;
        }
        return platform.getId().equals(filteredPlatform.getId());
    }

    public boolean isFilteredBy(Origin origin) {
        if (filteredOrigin == null) {
            return false;
        }
        return origin.getId().equals(filteredOrigin.getId());
    }

    public int getNumWishesInThisOrigin(Origin origin) {
        Collection<Wish> articles = newInitialWishesList();
        if (filteredPlatform != null) {
            WishPredicates.PlatformPredicate filterByPlatform = new WishPredicates.PlatformPredicate(filteredPlatform);
            articles = filter(articles, filterByPlatform);
        }
        WishPredicates.OriginPredicate filterByOrigin = new WishPredicates.OriginPredicate(origin);
        articles = filter(articles, filterByOrigin);
        return articles.size();
    }

    public int getNumWishesInThisPlatform(Platform platform) {
        Collection<Wish> wishes = newInitialWishesList();
        wishes = filter(wishes, new WishPredicates.PlatformPredicate(platform));
        return wishes.size();
    }

    private Collection<Wish> newInitialWishesList() {
        Collection<Wish> wishes = new ArrayList<Wish>();
        if (!consolidated) {
            if (filteredByGames) {
                if (filteredUser == null) {
                    wishes.addAll(wishBox.findAllGames());
                } else {
                    wishes.addAll(filteredUser.getAllWishedGames());
                }
            } else {
                if (filteredUser == null) {
                    wishes.addAll(new ArrayList<Wish>(wishBox.findAllHardwares()));
                } else {
                    wishes.addAll(filteredUser.getAllWishedHardwares());
                }
            }
        }
        return wishes;
    }

    public User getFilteredUser() {
        return filteredUser;
    }

    public boolean isFilteredByGames() {
        return filteredByGames;
    }

    public long getNumHardwares() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedHardwares();
        }
        return wishBox.getNumWishedHardwares();
    }

    public long getNumGames() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedGames();
        }
        return wishBox.getNumWishedGames();
    }

}
