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
public class Tag extends AbstractEntity implements Comparable<Tag> {

	@Column(nullable = false, unique = true)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String name;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Article article;

	/* package */Tag() {
	}

	public Tag(String name, Article article) {
		this.name = name;
		this.article = article;
	}

	public String getName() {
		return name;
	}

	public Article getArticle() {
		return article;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Tag tag) {
		return name.compareToIgnoreCase(tag.name);
	}

	public boolean hasName(String name) {
		return this.name.equalsIgnoreCase(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			return ((Tag) obj).hasName(name);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
