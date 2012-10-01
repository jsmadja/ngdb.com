package com.ngdb;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

public class ForumCode {

    public static String asVBulletinCode(Collection<ShopItem> shopItems) {
        StringBuilder sb = new StringBuilder();

        for (ShopItem shopItem : shopItems) {
            Article article = shopItem.getArticle();
            Double priceInEuros = shopItem.getPriceInEuros();
            String details = shopItem.getDetails();
            String title = shopItem.getTitle();
            String state = shopItem.getState().getTitle();
            Long id = shopItem.getId();
            String url = "http://neogeodb.com/shop/itemview/" + id;
            String origin = article.getOriginTitle();
            String originImageUrl = "http://neogeodb.com/img/flags/" + origin + ".png";
            String imageUrl = "http://www.neogeodb.com" + shopItem.getMainPicture().getUrlSmall();

            sb.append(format("[IMG]%s[/IMG] [URL=%s]%s[/URL] - [B][COLOR=Red]%s[/COLOR][/B] - [B][COLOR=SeaGreen]%s euros[/COLOR][/B]", originImageUrl, url, title, state, priceInEuros));
            sb.append(format("\n[I]%s[/I]", details));
            if (shopItem.hasNoPicture() && shopItem.hasCover()) {
                sb.append(format("\n[URL=%s][IMG]%s[/IMG][/URL]", url, "http://www.neogeodb.com" + article.getCover().getUrl("small")));
            } else {
                sb.append(format("\n[URL=%s][IMG]%s[/IMG][/URL]", url, imageUrl));
            }
            sb.append("\n\n");
        }
        return sb.toString();
    }

}
