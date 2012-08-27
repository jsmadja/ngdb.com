package com.ngdb.entities.shop;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PotentialBuys implements Serializable {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PotentialBuyers", inverseJoinColumns = { @JoinColumn(name = "shop_item_id") }, joinColumns = { @JoinColumn(name = "user_id") })
    private Set<ShopItem> potentialBuys;

    public Set<ShopItem> all() {
        return Collections.unmodifiableSet(potentialBuys);
    }

}
