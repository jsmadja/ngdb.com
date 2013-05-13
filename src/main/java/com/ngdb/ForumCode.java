package com.ngdb;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.services.infrastructure.PictureService;

import java.util.Collection;

import static java.lang.String.format;

public class ForumCode {

    public static String asVBulletinCode(Collection<ShopItem> shopItems, PictureService pictureService) {
        StringBuilder sb = new StringBuilder();
        for (ShopItem shopItem : shopItems) {
            insertVBulletinShopItem(sb, shopItem, pictureService.getCoverOf(shopItem));
            sb.append("\n\n");
        }
        sb.append("[SIZE=\"1\"]generated from www.neogeodb.com[/SIZE]");
        return sb.toString();
    }

    private static void insertVBulletinShopItem(StringBuilder sb, ShopItem shopItem, Picture cover) {
        Article article = shopItem.getArticle();
        Double price = shopItem.getPriceInCustomCurrency();
        String details = shopItem.getDetails();
        String title = shopItem.getTitle();
        String state = shopItem.getState().getTitle();
        String origin = article.getOriginTitle();
        String originImageUrl = "http://neogeodb.com/img/flags/" + origin + ".png";
        String imageUrl = "http://www.neogeodb.com" + cover.getUrlSmall();
        String customCurrency = shopItem.getCustomCurrency();

        sb.append(format("[IMG]%s[/IMG] %s - [B][COLOR=Red]%s[/COLOR][/B] - [B][COLOR=SeaGreen]%s %s[/COLOR][/B]", originImageUrl, title, state, customCurrency, price));
        sb.append(format("\n[I]%s[/I]", details));
        if (shopItem.hasNoPicture() && shopItem.hasCover()) {
            sb.append(format("\n[IMG]%s[/IMG]", "http://www.neogeodb.com" + article.getCover().getUrl("small")));
        } else {
            sb.append(format("\n[IMG]%s[/IMG]", imageUrl));
        }
    }

    public static String asPhpBBCode(Collection<ShopItem> shopItems, PictureService pictureService) {
        StringBuilder sb = new StringBuilder();
        for (ShopItem shopItem : shopItems) {
            insertPhpBBShopItem(sb, shopItem, pictureService.getCoverOf(shopItem));
            sb.append("\n\n");
        }
        sb.append("[size=9]generated from www.neogeodb.com[/size]");
        return sb.toString();
    }

    private static void insertPhpBBShopItem(StringBuilder sb, ShopItem shopItem, Picture cover) {
        Article article = shopItem.getArticle();
        Double price = shopItem.getPriceInCustomCurrency();
        String details = shopItem.getDetails();
        String title = shopItem.getTitle();
        String state = shopItem.getState().getTitle();
        String origin = article.getOriginTitle();
        String originImageUrl = "http://neogeodb.com/img/flags/" + origin + ".png";
        String imageUrl = "http://www.neogeodb.com" + cover.getUrlSmall();
        String customCurrency = shopItem.getCustomCurrency();

        sb.append(format("[img]%s[/img] %s - [b][color=red]%s[/color][/b] - [b][color=green]%s %s[/color][/b]", originImageUrl, title, state, customCurrency, price));
        sb.append(format("\n[i]%s[/i]", details));
        if (shopItem.hasNoPicture() && shopItem.hasCover()) {
            sb.append(format("\n[img]%s[/img]", "http://www.neogeodb.com" + article.getCover().getUrl("small")));
        } else {
            sb.append(format("\n[url=%s][img]%s[/img][/url]", imageUrl, imageUrl));
        }
    }
}
