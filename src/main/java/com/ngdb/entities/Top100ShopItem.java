package com.ngdb.entities;

public class Top100ShopItem {

    private String price;
    private Long shopItemId;
    private Long articleId;
    private String title;
    private String originTitle;

    public Top100ShopItem(Long articleId, String title, String originTitle, String price, Long shopItemId) {
        this.articleId = articleId;
        this.title = title;
        this.originTitle = originTitle;
        this.price = price;
        this.shopItemId = shopItemId;
    }

    public String getPrice() {
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

    public String getOriginTitle() {
        return originTitle;
    }
}
