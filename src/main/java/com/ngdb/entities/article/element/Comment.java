package com.ngdb.entities.article.element;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.User;
import com.ngdb.services.ToStringBridge;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;
import org.ocpsoft.pretty.time.PrettyTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Locale;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment extends AbstractEntity implements Comparable<Comment> {

	private static final int MAX_COMMENT_LENGTH = 1024;

	@Column(nullable = false, length = 1024)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String text;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
    @FieldBridge(impl = ToStringBridge.class)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private User author;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Article article;

	Comment() {
	}

	public Comment(String text, User author, Article article) {
		this.article = article;
		int end = text.length() < MAX_COMMENT_LENGTH ? text.length() : MAX_COMMENT_LENGTH;
		this.text = text.substring(0, end);
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

	@Override
	public int compareTo(Comment comment) {
		return comment.getCreationDate().compareTo(this.getCreationDate());
	}
}
