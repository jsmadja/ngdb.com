package com.ngdb.entities.shop;

import com.ngdb.entities.user.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static java.util.Collections.sort;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Basket implements Serializable, Iterable<ShopItem> {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PotentialBuyers", inverseJoinColumns = { @JoinColumn(name = "shop_item_id") }, joinColumns = { @JoinColumn(name = "user_id") })
    private Set<ShopItem> shopItems;

    public Set<ShopItem> all() {
        return Collections.unmodifiableSet(shopItems);
    }

    public List<User> allSellers() {
        List<User> sellers = new ArrayList<User>();
        for(ShopItem shopItem: shopItems) {
            User seller = shopItem.getSeller();
            if(!sellers.contains(seller)) {
                sellers.add(seller);
            }
        }
        sort(sellers);
        return sellers;
    }

    public List<ShopItem> allItemsForSaleBy(User seller) {
        List<ShopItem> shopItems = new ArrayList<ShopItem>();
        for(ShopItem shopItem: this.shopItems) {
            if(seller.getId().equals(shopItem.getSeller().getId())) {
                shopItems.add(shopItem);
            }
        }
        sort(shopItems);
        return shopItems;
    }

    public String getTotalFor(User seller) {
        double sum = 0;
        List<ShopItem> shopItems = allItemsForSaleBy(seller);
        for (ShopItem shopItem : shopItems) {
            sum += shopItem.getPriceInDollars();
        }
        return "$"+sum;
    }

    public void removeFromBasket(ShopItem shopItem) {
        shopItems.remove(shopItem);
    }

    @Override
    public Iterator<ShopItem> iterator() {
        return shopItems.iterator();
    }

    public int getNumArticles() {
        return shopItems.size();
    }
}
