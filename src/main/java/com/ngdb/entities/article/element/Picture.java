package com.ngdb.entities.article.element;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Picture extends AbstractEntity implements Comparable<Picture> {

    public static final Picture EMPTY = new Picture("/ngdb/unknown.png");

    @Column(nullable = false)
    private String url;

    @ManyToOne(optional = true)
    private Article article;

    @ManyToOne(optional = true)
    private ShopItem shopItem;

    Picture() {
    }

    public Picture(String url) {
        this.url = url;
    }

    public String getUrl() {
        if (url == null) {
            return EMPTY.getUrl();
        }
        if (isWatermarked()) {
            return toWatermarkedUrl();
        }
        return url;
    }

    public String getUrl(String size) {
        if (url == null) {
            return EMPTY.getUrl();
        }
        if (isAvailableIn(size)) {
            return toSizeUrl(size);
        }
        return url;
    }

    public String toWatermarkedUrl() {
        return addSuffix("high");
    }

    public String toSizeUrl(String size) {
        return addSuffix(size);
    }

    private String addSuffix(String suffix) {
        StringBuilder sizeUrl = new StringBuilder(url);
        sizeUrl.insert(url.lastIndexOf("."), "_" + suffix);
        return sizeUrl.toString();
    }

    private boolean isWatermarked() {
        return new File(toWatermarkedUrl()).exists();
    }

    private boolean isAvailableIn(String size) {
        return new File(toSizeUrl(size)).exists();
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    @Override
    public String toString() {
        return url;
    }

    @Override
    public int compareTo(Picture p) {
        return getCreationDate().compareTo(p.getCreationDate());
    }

}
