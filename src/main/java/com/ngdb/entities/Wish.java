package com.ngdb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Wish {

	@EmbeddedId
	private WishId id;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User wisher;

	@ManyToOne
	@JoinColumn(name = "article_id", insertable = false, updatable = false)
	private Article article;

	public Article getArticle() {
		return article;
	}

	public User getWisher() {
		return wisher;
	}

	@Embeddable
	public static class WishId implements Serializable {

		@Column(name = "user_id", nullable = false, updatable = false)
		private Long userId;

		@Column(name = "article_id", nullable = false, updatable = false)
		private Long articleId;

		public boolean equals(Object o) {
			if (o == null)
				return false;

			if (!(o instanceof WishId))
				return false;

			WishId other = (WishId) o;
			if (!(other.articleId.equals(articleId)))
				return false;

			if (!(other.userId.equals(userId)))
				return false;

			return true;
		}
	}

}
