package com.ngdb.entities.article.element;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class File  extends AbstractEntity implements Comparable<File> {

    @Column(nullable = false)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String type;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Article article;

    /* package */File() {
    }

    public File(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public File(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(File file) {
        return name.compareToIgnoreCase(file.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof File) {
            return ((File) obj).url.equalsIgnoreCase(url);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public Article getArticle() {
        return article;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
