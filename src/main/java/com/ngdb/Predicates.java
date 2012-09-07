package com.ngdb;

import java.util.Date;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;

public class Predicates {

    public static final Predicate<Article> hasPicture = new Predicate<Article>() {
        @Override
        public boolean apply(@Nullable Article input) {
            return input.getPictures().count() > 0;
        }
    };

    public static Predicate<ShopItem> shopItemsForSale = new Predicate<ShopItem>() {
        @Override
        public boolean apply(ShopItem shopItem) {
            return !shopItem.isSold();
        }
    };

    public static Predicate<Article> isGame = new Predicate<Article>() {
        @Override
        public boolean apply(Article input) {
            return input.isGame();
        }
    };

    public static Predicate<Article> isHardware = new Predicate<Article>() {
        @Override
        public boolean apply(Article input) {
            return input.isHardware();
        }
    };

    public static Predicate<Article> isAccessory = new Predicate<Article>() {
        @Override
        public boolean apply(Article input) {
            return input.isAccessory();
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
            return platformShortName.equals(platform);
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
            return originTitle.equals(origin);
        }

    }

    public static class PublisherPredicate implements Predicate<Article> {
        private String name;

        public PublisherPredicate(Publisher publisher) {
            this.name = publisher.getName();
        }

        @Override
        public boolean apply(Article article) {
            Publisher publisher = article.getPublisher();
            if (publisher == null) {
                return false;
            }
            String publisherName = publisher.getName();
            return name.equalsIgnoreCase(publisherName);
        }
    }

    public static class NghPredicate implements Predicate<Article> {

        private String ngh;

        public NghPredicate(String ngh) {
            this.ngh = ngh;
        }

        @Override
        public boolean apply(Article input) {
            if (input.isGame()) {
                Game game = (Game) input;
                String ngh = game.getNgh();
                if (ngh != null) {
                    return ngh.equalsIgnoreCase(this.ngh);
                }
            }
            return false;
        }

    }

    public static class TagPredicate implements Predicate<Article> {

        private Tag tag;

        public TagPredicate(Tag tag) {
            this.tag = tag;
        }

        @Override
        public boolean apply(Article input) {
            return input.containsTag(tag);
        }
    }

    public static class ReleaseDatePredicate implements Predicate<Article> {

        private Date releaseDate;

        public ReleaseDatePredicate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }

        @Override
        public boolean apply(Article input) {
            Date inputReleaseDate = input.getReleaseDate();
            return inputReleaseDate != null && inputReleaseDate.equals(releaseDate);
        }
    }

    public static class Matching implements Predicate<Article> {

        private String pattern;

        public Matching(String searchItem) {
            this.pattern = searchItem;
        }

        @Override
        public boolean apply(Article article) {
            return foundInTitle(article) || foundInTags(article);
        }

        private boolean foundInTags(Article article) {
            return article.containsTag(pattern);
        }

        private boolean foundInTitle(Article article) {
            return article.getTitle().toLowerCase().contains(pattern);
        }
    }

}