package com.ngdb.entities.user;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Predicates.shopItemsForSale;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.ShopItemPredicates;
import com.ngdb.entities.shop.ShopItem;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shop {

    @OneToMany(mappedBy = "seller")
    private Set<ShopItem> shopItems;

    public Set<ShopItem> getShopItems() {
        return Collections.unmodifiableSet(shopItems);
    }

    public Collection<ShopItem> getShopItemsToSell() {
        return filter(shopItems, shopItemsForSale);
    }

    public int getNumArticlesToSell() {
        return getShopItemsToSell().size();
    }

    public boolean contains(ShopItem shopItem) {
        return shopItems.contains(shopItem);
    }

    public long getNumHardwaresForSale() {
        return getAllHardwaresForSale().size();
    }

    public long getNumGamesForSale() {
        return getAllGamesForSale().size();
    }

    public Collection<ShopItem> getAllGamesForSale() {
        return filter(shopItems, ShopItemPredicates.gamesForSale);
    }

    public Collection<ShopItem> getAllHardwaresForSale() {
        return filter(shopItems, ShopItemPredicates.hardwaresForSale);
    }
}
