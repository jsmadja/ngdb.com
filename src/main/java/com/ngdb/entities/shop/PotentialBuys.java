package com.ngdb.entities.shop;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.ngdb.entities.user.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import static java.util.Collections.sort;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PotentialBuys implements Serializable {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PotentialBuyers", inverseJoinColumns = { @JoinColumn(name = "shop_item_id") }, joinColumns = { @JoinColumn(name = "user_id") })
    private Set<ShopItem> potentialBuys;

    public Set<ShopItem> all() {
        return Collections.unmodifiableSet(potentialBuys);
    }

    public List<User> allSellers() {
        List<User> sellers = new ArrayList<User>();
        for(ShopItem shopItem:potentialBuys) {
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
        for(ShopItem shopItem:potentialBuys) {
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
}
