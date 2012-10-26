package com.ngdb.services;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.shop.ShopItem;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Set;

public class Cacher {

    private static Cache averageMarksOfGames, reviewsOfGames, ranksOfArticles, coversOfArticles, coversOfShopItems,
    barcodes, wishRanksOfArticles;

    static {
        CacheManager cacheManager = CacheManager.create();
        averageMarksOfGames = cacheManager.getCache("average.marks.of.games");
        reviewsOfGames = cacheManager.getCache("reviews.of.games");
        ranksOfArticles = cacheManager.getCache("ranks.of.articles");
        wishRanksOfArticles = cacheManager.getCache("wishranks.of.articles");
        coversOfArticles = cacheManager.getCache("covers.of.articles");
        coversOfShopItems = cacheManager.getCache("covers.of.shopitems");
        barcodes = cacheManager.getCache("barcodes");
    }

    // --- has

    public boolean hasAverageMarkOf(Article article) {
        if(!article.isGame()) {
            return false;
        }
        return averageMarksOfGames.isKeyInCache(nghKeyFrom(article));
    }

    public boolean hasReviewsOf(Article article) {
        if(!article.isGame()) {
            return false;
        }
        return reviewsOfGames.isKeyInCache(nghKeyFrom(article));
    }

    public boolean hasRankOf(Article article) {
        return ranksOfArticles.isKeyInCache(keyFrom(article));
    }

    public boolean hasWishRankOf(Article article) {
        return wishRanksOfArticles.isKeyInCache(keyFrom(article));
    }

    public boolean hasCoverOf(Article article) {
        return coversOfArticles.isKeyInCache(keyFrom(article));
    }

    public boolean hasCoverOf(ShopItem shopItem) {
        return coversOfShopItems.isKeyInCache(keyFrom(shopItem));
    }

    public boolean hasBarcodeOf(String ean) {
        return barcodes.isKeyInCache(ean);
    }

    // --- set

    public void setAverageMarkOf(Article article, Double averageMark) {
        if(article.isGame()) {
            averageMarksOfGames.put(new Element(nghKeyFrom(article), averageMark));
        }
    }

    public void setReviewsOf(Article article, Set<Review> reviews) {
        if(article.isGame()) {
            reviewsOfGames.put(new Element(nghKeyFrom(article), reviews));
        }
    }

    public void setRankOf(Article article, int rank) {
        ranksOfArticles.put(new Element(keyFrom(article), rank));
    }

    public void setWishRankOf(Article article, int rank) {
        wishRanksOfArticles.put(new Element(keyFrom(article), rank));
    }

    public void setCoverOf(Article article, Picture picture) {
        coversOfArticles.put(new Element(keyFrom(article), picture));
    }

    public void setCoverOf(ShopItem shopItem, Picture picture) {
        coversOfShopItems.put(new Element(keyFrom(shopItem), picture));
    }

    public void setBarcodeOf(String ean, String barcode) {
        barcodes.put(new Element(ean, barcode));
    }

    // --- get

    public Double getAverageMarkOf(Article article) {
        return (Double) averageMarksOfGames.get(nghKeyFrom(article)).getValue();
    }

    public Set<Review> getReviewsOf(Article article) {
        return (Set<Review>) reviewsOfGames.get(nghKeyFrom(article)).getValue();
    }

    public int getRankOf(Article article) {
        return (Integer) ranksOfArticles.get(keyFrom(article)).getValue();
    }

    public int getWishRankOf(Article article) {
        return (Integer) wishRanksOfArticles.get(keyFrom(article)).getValue();
    }

    public Picture getCoverOf(Article article) {
        return (Picture) coversOfArticles.get(keyFrom(article)).getValue();
    }

    public Picture getCoverOf(ShopItem shopItem) {
        return (Picture) coversOfShopItems.get(keyFrom(shopItem)).getValue();
    }

    public String getBarcodeOf(String ean) {
        return barcodes.get(ean).getValue().toString();
    }

    // --- invalidate

    public void invalidateAverageMarkOf(Article article) {
        if(hasAverageMarkOf(article)) {
            averageMarksOfGames.remove(nghKeyFrom(article));
        }
    }

    public void invalidateReviewsOf(Article article) {
        if(hasReviewsOf(article)) {
            reviewsOfGames.remove(nghKeyFrom((Game) article));
        }
    }

    public void invalidateRanks() {
        ranksOfArticles.removeAll();
    }

    public void invalidateWishRanks() {
        wishRanksOfArticles.removeAll();
    }

    public void invalidateCoverOf(Article article) {
        if(hasCoverOf(article)) {
            coversOfArticles.remove(keyFrom(article));
        }
    }

    public void invalidateCoverOf(ShopItem shopItem) {
        if(hasCoverOf(shopItem)) {
            coversOfShopItems.remove(keyFrom(shopItem));
        }
    }

    public void invalidateBarcode(String ean) {
        if(hasBarcodeOf(ean)) {
            barcodes.remove(ean);
        }
    }

    // --- key

    private String nghKeyFrom(Article article) {
        return keyFrom((Game) article);
    }

    private Long keyFrom(Article article) {
        return article.getId();
    }

    private String keyFrom(Game game) {
        return game.getNgh();
    }

    private Long keyFrom(ShopItem shopItem) {
        return shopItem.getId();
    }

}
