package com.ngdb.entities.shop;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.user.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(optional = false, fetch = LAZY)
    private User buyer;

    @Column(nullable = false, length = 10240)
    private String bill;

    @OneToMany(mappedBy = "order")
    private Set<ShopItem> shopItems = new HashSet<ShopItem>();

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    public ShopOrder() {
        this.creationDate = this.modificationDate = new Date();
    }

    @PreUpdate
    public void update() {
        this.modificationDate = new Date();
    }

    public void addBill(String bill) {
        this.bill = bill;
    }

    public void addShopItem(ShopItem shopItem) {
        shopItems.add(shopItem);
        shopItem.setOrder(this);
    }

    public User getBuyer() {
        return buyer;
    }

    public Set<ShopItem> getShopItems() {
        return Collections.unmodifiableSet(shopItems);
    }

    public User getSeller() {
        return shopItems.iterator().next().getSeller();
    }

    public String getBill() {
        return bill;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
