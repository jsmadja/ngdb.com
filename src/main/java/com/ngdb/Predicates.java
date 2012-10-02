package com.ngdb;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;

import javax.annotation.Nullable;

public class Predicates {

    public static Predicate<ShopItem> shopItemsForSale = new Predicate<ShopItem>() {
        @Override
        public boolean apply(ShopItem shopItem) {
            return !shopItem.isSold();
        }
    };

    public static Predicate<Wish> isGameWish = new Predicate<Wish>() {
        @Override
        public boolean apply(@Nullable Wish input) {
            return input.getArticle().isGame();
        }
    };

    public static Predicate<Wish> isHardwareWish = new Predicate<Wish>() {
        @Override
        public boolean apply(@Nullable Wish input) {
            return input.getArticle().isHardware();
        }
    };
    public static Predicate<Wish> isAccessoryWish = new Predicate<Wish>() {
        @Override
        public boolean apply(@Nullable Wish input) {
            return input.getArticle().isAccessory();
        }
    };

    public static class PlatformPredicate implements Predicate<Article> {
        private String platformShortName;

        public PlatformPredicate(Platform platform) {
            this.platformShortName = platform.getShortName();
        }

        @Override
        public boolean apply(Article article) {
            String platform = article.getPlatformShortName();
            return platformShortName.equalsIgnoreCase(platform);
        }

    }

    public static class OriginPredicate implements Predicate<Article> {
        private String originTitle;

        public OriginPredicate(Origin origin) {
            this.originTitle = origin.getTitle();
        }

        @Override
        public boolean apply(Article article) {
            String origin = article.getOriginTitle();
            return originTitle.equalsIgnoreCase(origin);
        }

    }

}