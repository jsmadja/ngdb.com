package com.ngdb.entities.article.element;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.File;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Picture extends AbstractEntity implements Comparable<Picture> {

    @Column(nullable = false)
    private String url;

    @ManyToOne(optional = true)
    private Article article;

    Picture() {
    }

    public Picture(String url) {
        this.url = url;
    }

    public String getUrl() {
        if (isWatermarked()) {
            return toWatermarkedUrl();
        }
        return url;
    }

    public static Picture emptyOfHardware() {
        return new Picture("/ngdb/Hard_Wanted.jpg");
    }

    public static Picture emptyOf(String platformName) {
        return new Picture("/ngdb/" + platformName + "_Wanted.jpg");
    }

    public String getUrlSmall() {
        return getUrl("small");
    }

    public String getUrlMedium() {
        return getUrl("medium");
    }

    public String getUrlHigh() {
        return getUrl("high");
    }

    public String getUrl(String size) {
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

    @Override
    public String toString() {
        return url;
    }

    @Override
    public int compareTo(Picture p) {
        return getCreationDate().compareTo(p.getCreationDate());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Picture) {
            Picture p = (Picture) o;
            return url.equals(p.url);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    public String getOriginalUrl() {
        return url;
    }

    public boolean isNotEmpty() {
        return url != null;
    }
}
