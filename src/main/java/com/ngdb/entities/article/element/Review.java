package com.ngdb.entities.article.element;

import com.ngdb.Mark;
import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review extends AbstractEntity implements Comparable<Review> {

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String label;

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String url;

    private String mark;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    /* package */Review() {
    }

    public Review(String label, String url, String mark, Article article) {
        this.label = label;
        this.url = url;
        this.mark = mark;
        this.article = article;
    }

    public String getLabel() {
        return label;
    }

    public String getMark() {
        return new Mark(mark).toString();
    }

    public int getMarkInPercent() {
        return new Mark(mark).getAsPercent();
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return label + " " + url + " " + mark;
    }

    @Override
    public int compareTo(Review review) {
        if (getMark().compareToIgnoreCase(review.getMark()) == 0) {
            return getLabel().compareTo(review.getLabel());
        }
        return review.getMark().compareToIgnoreCase(getMark());
    }

    public double getMarkOn5() {
        return new Mark(mark).getStarsAsDouble();
    }

    public String getMarkAsStars() {
        return new Mark(mark).getStars();
    }
}
