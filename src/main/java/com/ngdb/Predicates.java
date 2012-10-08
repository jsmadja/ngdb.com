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

}