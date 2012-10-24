package com.ngdb.entities;

import org.joda.time.DateMidnight;

import java.util.Date;

public class Top100ShopItem {

    private double price;
    private Long shopItemId;
    private Long articleId;
    private String title;
    private String currency;
    private String originTitle;
    private Long sellerId;
    private String state;
    private Date saleDate;

    public Top100ShopItem(Long articleId, String title, String originTitle, double price, String currency, Long shopItemId, long sellerId, String state, Date saleDate) {
        this.articleId = articleId;
        this.title = title;
        this.originTitle = originTitle;
        this.price = price;
        this.currency = currency;
        this.shopItemId = shopItemId;
        this.sellerId = sellerId;
        this.state = state;
        this.saleDate = saleDate;
    }

    public double getPrice() {
        return price;
    }

    public Long getShopItemId() {
        return shopItemId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public String getTitle() {
        return title;
    }

    public String getCurrency() {
        return currency;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getState() {
        return state;
    }

    public Date getSaleDate() {
        return saleDate;
    }
}
