package com.ngdb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Collection {

	@EmbeddedId
	private CollectionId id;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "article_id", insertable = false, updatable = false)
	private Article article;

	public User getUser() {
		return user;
	}

	@Embeddable
	public static class CollectionId implements Serializable {

		@Column(name = "user_id", nullable = false, updatable = false)
		private Long userId;

		@Column(name = "article_id", nullable = false, updatable = false)
		private Long articleId;

		public boolean equals(Object o) {
			if (o == null)
				return false;

			if (!(o instanceof CollectionId))
				return false;

			CollectionId other = (CollectionId) o;
			if (!(other.articleId.equals(articleId)))
				return false;

			if (!(other.userId.equals(userId)))
				return false;

			return true;
		}
	}

}
