package com.ngdb.entities.article.element;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.ocpsoft.pretty.time.PrettyTime;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;

@Entity
public class Comment extends AbstractEntity {

	private static final int MAX_COMMENT_LENGTH = 1024;

	@Column(nullable = false, length = 1024)
	private String text;

	@ManyToOne(optional = false)
	private User author;

	@ManyToOne(optional = false)
	private Article article;

	Comment() {
	}

	public Comment(String text, User author, Article article) {
		this.article = article;
		String cleanedText = Jsoup.clean(text, Whitelist.none());
		int end = cleanedText.length() < MAX_COMMENT_LENGTH ? cleanedText.length() : MAX_COMMENT_LENGTH;
		this.text = cleanedText.substring(0, end);
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public User getAuthor() {
		return author;
	}

	public String getPostDate() {
		return new PrettyTime(Locale.UK).format(getCreationDate());
	}

	public Article getArticle() {
		return article;
	}
}
