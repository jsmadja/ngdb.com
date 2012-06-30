package com.ngdb.entities.article.element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag extends AbstractEntity implements Comparable<Tag> {

	@Column(nullable = false, unique = true)
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
