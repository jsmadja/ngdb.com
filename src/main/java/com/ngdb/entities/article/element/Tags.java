package com.ngdb.entities.article.element;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Embeddable
public class Tags {

	@ManyToMany
	@JoinTable(name = "ArticleTags", inverseJoinColumns = { @JoinColumn(name = "tag_id") }, joinColumns = { @JoinColumn(name = "article_id") })
	private Set<Tag> tags = new HashSet<Tag>();

	public void add(Tag tag) {
		this.tags.add(tag);
	}

}
