package com.ngdb.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class WishList {

	@EmbeddedId
	private WishListId id;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "article_id", insertable = false, updatable = false)
	private Article article;

	@Embeddable
	public static class WishListId implements Serializable {

		@Column(name = "user_id", nullable = false, updatable = false)
		private Long userId;

		@Column(name = "article_id", nullable = false, updatable = false)
		private Long articleId;

		public boolean equals(Object o) {
			if (o == null)
				return false;

			if (!(o instanceof WishListId))
				return false;

			WishListId other = (WishListId) o;
			if (!(other.articleId.equals(articleId)))
				return false;

			if (!(other.userId.equals(userId)))
				return false;

			return true;
		}
	}

}
