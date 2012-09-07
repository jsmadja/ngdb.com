package com.ngdb.entities;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.ngdb.ShopItemPredicates;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.ShopItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Collections2.filter;

public class MarketFilter extends AbstractFilter {

    private Market market;

    private Collection<ShopItem> allShopItems;

    public MarketFilter(com.ngdb.entities.Market market) {
        this.market = market;
        clear();
    }

    private Collection<ShopItem> applyFilters(List<Predicate<ShopItem>> filters) {
        Collection<ShopItem> filteredShopItems = allShopItems();
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

    public int getNumShopItemsInThisOrigin(Origin origin) {
        Collection<ShopItem> articles = allShopItems();
        if (filteredPlatform != null) {
            ShopItemPredicates.PlatformPredicate filterByPlatform = new ShopItemPredicates.PlatformPredicate(filteredPlatform);
            articles = filter(articles, filterByPlatform);
        }
        ShopItemPredicates.OriginPredicate filterByOrigin = new ShopItemPredicates.OriginPredicate(origin);
        articles = filter(articles, filterByOrigin);
        return articles.size();
    }

    public int getNumShopItemsInThisPlatform(Platform platform) {
        Collection<ShopItem> shopItems = allShopItems();
        shopItems = filter(shopItems, new ShopItemPredicates.PlatformPredicate(platform));
        return shopItems.size();
    }

    private Collection<ShopItem> allShopItems() {
        if (filteredByGames) {
            if (filteredUser == null) {
                allShopItems = new ArrayList<ShopItem>(market.findAllGamesForSale());
            } else {
                allShopItems = market.getAllGamesForSaleBy(filteredUser);
            }
        } else if(filteredByHardwares){
            if (filteredUser == null) {
                allShopItems = new ArrayList<ShopItem>(market.findAllHardwaresForSale());
            } else {
                allShopItems = market.getAllHardwaresForSaleBy(filteredUser);
            }
        } else {
            if (filteredUser == null) {
                allShopItems = new ArrayList<ShopItem>(market.findAllAccessoriesForSale());
            } else {
                allShopItems = market.getAllAccessoriesForSaleBy(filteredUser);
            }
        }
        return new ArrayList<ShopItem>(allShopItems);
    }

    public long getNumHardwares() {
        if (filteredUser != null) {
            return market.getNumHardwaresForSaleBy(filteredUser);
        }
        return market.getNumHardwaresForSale();
    }

    public long getNumAccessories() {
        if (filteredUser != null) {
            return market.getNumAccessoriesForSaleBy(filteredUser);
        }
        return market.getNumAccessoriesForSale();
    }

    public long getNumGames() {
        if (filteredUser != null) {
            return market.getNumGamesForSaleBy(filteredUser);
        }
        return market.getNumGamesForSale();
    }

}
