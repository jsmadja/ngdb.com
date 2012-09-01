package com.ngdb.entities.article.element;

import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang.StringUtils.remove;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Review extends AbstractEntity implements Comparable<Review> {

    private String label;
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
        if (mark == null) {
            return "00";
        }
        if (mark.contains("%")) {
            return convertPercents();
        } else if (mark.contains("/")) {
            return convertQuotient();
        }
        return "00";
    }

    public int getMarkInPercent() {
        if (mark == null) {
            return 0;
        }
        if (mark.contains("%")) {
            return Integer.valueOf(remove(mark, '%'));
        } else if (mark.contains("/")) {
            Double numerator = Double.valueOf(mark.split("/")[0]);
            Double denominator = Double.valueOf(mark.split("/")[1]);
            return (int) ((numerator / denominator) * 100);
        }
        return 0;
    }

    private String convertQuotient() {
        double inf = Double.valueOf(mark.split("/")[1]);
        if (inf == 0) {
            return "00";
        }
        double sup = Double.valueOf(mark.split("/")[0]);
        Double i = new BigDecimal((sup * 10) / inf).setScale(0, HALF_UP).doubleValue() / 2;
        return i.toString().replace(".", "").substring(0, 2);
    }

    private String convertPercents() {
        Double value = Double.parseDouble(mark.replaceAll("%", "")) / 2;

        value = round(value);

        String string = Integer.toString(value.intValue());
        if (string.equals("0")) {
            string = "00";
        }
        return string;
    }

    private Double round(Double value) {
        value = value / 10;
        Double v = new Double(value.intValue());
        double diff = value - v;
        if (diff < 0.25) {
            value = v;
        } else if (diff >= 0.75) {
            value = v + 1;
        } else if (diff >= 0.25 && diff < 0.5) {
            value = v;
        } else if (diff >= 0.5 && diff <= 0.74) {
            value = v + 0.5;
        }
        return value * 10;
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
}
