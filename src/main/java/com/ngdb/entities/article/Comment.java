package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.user.User;

@Entity
public class Comment extends AbstractEntity {

	@Column(nullable = false)
	private String text;

	@ManyToOne(optional = false)
	private User author;

	@ManyToOne(optional = false)
	private Article article;

	public Comment() {
	}

	public Comment(String text, User author, Article article) {
		this.article = article;
		this.text = text;
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return author;
	}

	public String getAuthorName() {
		return author.getLogin();
	}

	public Long getAuthorId() {
		return author.getId();
	}

}
