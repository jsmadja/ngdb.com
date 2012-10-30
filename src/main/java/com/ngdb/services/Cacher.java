package com.ngdb.services;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.article.element.File;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.shop.ShopItem;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Set;

public class Cacher {

    private static Cache averageMarksOfGames, reviewsOfGames, ranksOfArticles, coversOfArticles, coversOfShopItems,
    barcodes, wishRanksOfArticles, commentsOfArticles, filesOfArticles;

    static {
        CacheManager cacheManager = CacheManager.create();
        averageMarksOfGames = cacheManager.getCache("average.marks.of.games");
        reviewsOfGames = cacheManager.getCache("reviews.of.games");
        ranksOfArticles = cacheManager.getCache("ranks.of.articles");
        wishRanksOfArticles = cacheManager.getCache("wishranks.of.articles");
        coversOfArticles = cacheManager.getCache("covers.of.articles");
        coversOfShopItems = cacheManager.getCache("covers.of.shopitems");
        commentsOfArticles = cacheManager.getCache("comments.of.articles");
        filesOfArticles = cacheManager.getCache("files.of.articles");
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

    public boolean hasCommentsOf(Article article) {
        if(article.isGame()) {
            return commentsOfArticles.isKeyInCache(nghKeyFrom(article));
        }
        return commentsOfArticles.isKeyInCache(keyFrom(article));
    }

    public boolean hasFilesOf(Article article) {
        if(article.isGame()) {
            return filesOfArticles.isKeyInCache(nghKeyFrom(article));
        }
        return filesOfArticles.isKeyInCache(keyFrom(article));
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

    public void setCommentsOf(Article article, Set<Comment> comments) {
        if(article.isGame()) {
            commentsOfArticles.put(new Element(nghKeyFrom(article), comments));
        } else {
            commentsOfArticles.put(new Element(keyFrom(article), comments));
        }
    }

    public void setFilesOf(Article article, Set<File> files) {
        if(article.isGame()) {
            filesOfArticles.put(new Element(nghKeyFrom(article), files));
        } else {
            filesOfArticles.put(new Element(keyFrom(article), files));
        }
    }

    // --- get

    public Double getAverageMarkOf(Article article) {
        return (Double) averageMarksOfGames.get(nghKeyFrom(article)).getValue();
    }

    public Set<Review> getReviewsOf(Article article) {
        return (Set<Review>) reviewsOfGames.get(nghKeyFrom(article)).getValue();
    }

    public Set<Comment> getCommentsOf(Article article) {
        if(article.isGame()) {
            return (Set<Comment>) commentsOfArticles.get(nghKeyFrom(article)).getValue();
        }
        return (Set<Comment>) commentsOfArticles.get(keyFrom(article)).getValue();
    }

    public Set<File> getFilesOf(Article article) {
        if(article.isGame()) {
            return (Set<File>) filesOfArticles.get(nghKeyFrom(article)).getValue();
        }
        return (Set<File>) filesOfArticles.get(keyFrom(article)).getValue();
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

    public void invalidateCommentsOf(Article article) {
        if(hasCommentsOf(article)) {
            if(article.isGame()) {
                commentsOfArticles.remove(nghKeyFrom(article));
            } else {
                commentsOfArticles.remove(keyFrom(article));
            }
        }
    }

    public void invalidateFilesOf(Article article) {
        if(hasFilesOf(article)) {
            if(article.isGame()) {
                filesOfArticles.remove(nghKeyFrom(article));
            } else {
                filesOfArticles.remove(keyFrom(article));
            }
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
