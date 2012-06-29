package com.ngdb.entities.article.element;

import static java.math.RoundingMode.HALF_UP;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
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

	@ManyToOne
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
		if (mark.contains("%")) {
			return convertPercents();
		} else if (mark.contains("/")) {
			return convertQuotient();
		}
		return "00";
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
		double value = Double.parseDouble(mark.replaceAll("%", ""));
		BigDecimal bigDecimal = new BigDecimal(value / 20D);
		Double i = bigDecimal.setScale(0, RoundingMode.HALF_UP).doubleValue();
		return i.toString().replace(".", "").substring(0, 2);
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
		return getMark().compareToIgnoreCase(review.getMark());
	}
}
