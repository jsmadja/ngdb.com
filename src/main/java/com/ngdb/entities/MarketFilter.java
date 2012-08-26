package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.ShopItemPredicates;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;

public class MarketFilter {

    private Market market;

    private boolean filteredByGames;

    private Origin filteredOrigin;

    private Platform filteredPlatform;

    private User filteredUser;

    private Article filteredArticle;

    private boolean consolidated;

    private Collection<ShopItem> initialShopItemList;

    public MarketFilter(com.ngdb.entities.Market market) {
        this.market = market;
        clear();
    }

    public void clear() {
        this.filteredOrigin = null;
        this.filteredPlatform = null;
        this.filteredUser = null;
        this.filteredArticle = null;
        this.consolidated = false;
        this.filteredByGames = true;
    }

    public String getQueryLabel() {
        String queryLabel = "all ";
        if (filteredByGames) {
            queryLabel += orange("games for sale");
        } else {
            queryLabel += orange("hardwares for sale");
        }
        if (filteredOrigin != null) {
            queryLabel += " from " + orange(filteredOrigin.getTitle());
        }
        if (filteredPlatform != null) {
            queryLabel += " on " + orange(filteredPlatform.getName());
        }
        if (filteredUser != null) {
            queryLabel += " owned by " + orange(filteredUser.getLogin());
        }
        if (filteredArticle != null) {
            queryLabel += " of article " + orange(filteredArticle.getTitle());
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

    public void filterByArticle(Article article) {
        this.filteredArticle = article;
    }

    private Collection<ShopItem> applyFilters(List<Predicate<ShopItem>> filters) {
        Collection<ShopItem> filteredShopItems = newInitialShopItemsList();
        buildFilters(filters);
        for (Predicate<ShopItem> filter : filters) {
            filteredShopItems = filter(filteredShopItems, filter);
        }
        return filteredShopItems;
    }

    private void buildFilters(List<Predicate<ShopItem>> filters) {
        if (filteredOrigin != null) {
            filters.add(new ShopItemPredicates.OriginPredicate(filteredOrigin));
        }
        if (filteredPlatform != null) {
            filters.add(new ShopItemPredicates.PlatformPredicate(filteredPlatform));
        }
        if (filteredArticle != null) {
            filters.add(new ShopItemPredicates.ArticlePredicate(filteredArticle));
        }
    }

    public List<ShopItem> getShopItems() {
        List<Predicate<ShopItem>> filters = Lists.newArrayList();
        Collection<ShopItem> filteredShopItems = applyFilters(filters);
        List<ShopItem> arrayList = new ArrayList<ShopItem>(filteredShopItems);
        Collections.sort(arrayList);
        return arrayList;
    }

    public void filterByGames() {
        this.filteredByGames = true;
        invalidateArticles();
    }

    public void filterByHardwares() {
        this.filteredByGames = false;
        invalidateArticles();
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

    private void invalidateArticles() {
        this.consolidated = false;
    }

    private void validateShopItems() {
        this.consolidated = true;
    }

    public int getNumShopItemsInThisOrigin(Origin origin) {
        Collection<ShopItem> articles = newInitialShopItemsList();
        if (filteredPlatform != null) {
            ShopItemPredicates.PlatformPredicate filterByPlatform = new ShopItemPredicates.PlatformPredicate(filteredPlatform);
            articles = filter(articles, filterByPlatform);
        }
        ShopItemPredicates.OriginPredicate filterByOrigin = new ShopItemPredicates.OriginPredicate(origin);
        articles = filter(articles, filterByOrigin);
        return articles.size();
    }

    public int getNumShopItemsInThisPlatform(Platform platform) {
        Collection<ShopItem> shopItems = newInitialShopItemsList();
        shopItems = filter(shopItems, new ShopItemPredicates.PlatformPredicate(platform));
        return shopItems.size();
    }

    private Collection<ShopItem> newInitialShopItemsList() {
        if (!consolidated) {
            if (filteredByGames) {
                if (filteredUser == null) {
                    initialShopItemList = new ArrayList<ShopItem>(market.findAllGamesForSale());
                } else {
                    initialShopItemList = filteredUser.getAllGamesForSale();
                }
            } else {
                if (filteredUser == null) {
                    initialShopItemList = new ArrayList<ShopItem>(market.findAllHardwaresForSale());
                } else {
                    initialShopItemList = filteredUser.getAllHardwaresForSale();
                }
            }
            validateShopItems();
        }
        return new ArrayList<ShopItem>(initialShopItemList);
    }

    public User getFilteredUser() {
        return filteredUser;
    }

    public boolean isFilteredByGames() {
        return filteredByGames;
    }

    public long getNumHardwares() {
        if (filteredUser != null) {
            return filteredUser.getNumHardwaresForSale();
        }
        return market.getNumHardwaresForSale();
    }

    public long getNumGames() {
        if (filteredUser != null) {
            return filteredUser.getNumGamesForSale();
        }
        return market.getNumGamesForSale();
    }

}
