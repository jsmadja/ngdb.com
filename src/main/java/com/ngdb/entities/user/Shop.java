package com.ngdb.entities.user;

import com.ngdb.entities.shop.ShopItem;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Shop {

    @OneToMany(mappedBy = "seller")
    private Set<ShopItem> shopItems;

    public Collection<ShopItem> all() {
        return Collections.unmodifiableCollection(shopItems);
    }

    public int getNumShopItems() {
        if(shopItems == null) {
            return 0;
        }
        return shopItems.size();
    }
}
