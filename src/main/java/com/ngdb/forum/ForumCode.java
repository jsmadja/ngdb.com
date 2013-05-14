package com.ngdb.forum;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.services.infrastructure.PictureService;

import java.util.Collection;

public abstract class ForumCode {

    private String HOST = "http://www.neogeodb.com";
    private Collection<ShopItem> shopItems;
    private PictureService pictureService;
    private StringBuilder sb;
    private ShopItem shopItem;
    private Article article;

    public ForumCode(Collection<ShopItem> shopItems, PictureService pictureService) {
        this.shopItems = shopItems;
        this.pictureService = pictureService;
    }

    public String generateCode() {
        sb = new StringBuilder();
        for (ShopItem shopItem : shopItems) {
            insertShopItem(shopItem);
        }
        sb.append(smallText("generated from " + HOST));
        return sb.toString();
    }

    private void insertShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
        this.article = shopItem.getArticle();
        sb.append(originFlag() + title() + " - " + coloredState() + " - " + coloredPrice());
        insertNewLine();
        sb.append(styledDetail());
        insertNewLine();
        if (printArticlePicture()) {
            sb.append(clickableArticlePicture());
        } else {
            sb.append(clickableShopItemPicture());
        }
        insertNewLine();
        insertNewLine();
    }

    private String clickableShopItemPicture() {
        return url(shopItemHighPicture(), img(shopItemSmallPicture()));
    }

    private String clickableArticlePicture() {
        return url(articleHighPicture(), img(articleSmallPicture()));
    }

    private String styledDetail() {
        return italic(shopItem.getDetails());
    }

    private String coloredPrice() {
        return bold(green(price()));
    }

    private String coloredState() {
        return bold(red(shopItem.getState().getTitle()));
    }

    private String originFlag() {
        return img(origin());
    }

    private String title() {
        return shopItem.getTitle();
    }

    private void insertNewLine() {
        sb.append("\n");
    }

    protected abstract String smallText(String value);

    private boolean printArticlePicture() {
        return shopItem.hasNoPicture() && shopItem.hasCover();
    }

    private String shopItemSmallPicture() {
        return HOST + pictureService.getCoverOf(shopItem).getUrlSmall();
    }

    private String shopItemHighPicture() {
        return HOST + pictureService.getCoverOf(shopItem).getUrlHigh();
    }

    private String articleSmallPicture() {
        return HOST + article.getCover().getUrlSmall();
    }

    private String articleHighPicture() {
        return HOST + article.getCover().getUrlHigh();
    }

    private String price() {
        String preferedCurrency = shopItem.getSeller().getPreferedCurrency();
        return shopItem.getPriceAsStringIn(preferedCurrency);
    }

    private String origin() {
        String origin = article.getOriginTitle();
        return HOST + "/img/flags/" + origin + ".png";
    }

    protected abstract String url(String url, String value);

    protected abstract String green(String value);

    protected abstract String red(String value);

    protected abstract String bold(String value);

    protected abstract String italic(String value);

    protected abstract String img(String value);
}
