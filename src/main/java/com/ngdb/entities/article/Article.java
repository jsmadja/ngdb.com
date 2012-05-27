package com.ngdb.entities.article;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.article.element.Comments;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Notes;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.Pictures;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Reviews;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.article.element.Tags;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.shop.ShopItems;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Article {

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "modification_date", nullable = false)
	private Date modificationDate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(name = "release_date")
	@Temporal(TemporalType.DATE)
	private Date releaseDate;

	@OneToOne
	private Origin origin;

	@Embedded
	private Notes notes;

	@Embedded
	private Tags tags;

	@Embedded
	private Pictures pictures;

	@Embedded
	private Reviews reviews;

	@Embedded
	private Comments comments;

	@OneToMany(mappedBy = "article")
	private Set<Wish> wishList;

	@Embedded
	private ShopItems shopItems;

	@OneToMany(mappedBy = "article")
	private Set<CollectionObject> owners;

	private String details;

	public Article() {
		creationDate = modificationDate = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		modificationDate = new Date();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Article) {
			Article a = (Article) obj;
			if (a.id.equals(id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, title);
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void addNote(Note note) {
		if (notes == null) {
			notes = new Notes();
		}
		notes.add(note);
		note.setArticle(this);
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addPicture(Picture picture) {
		pictures.add(picture);
	}

	public Pictures getPictures() {
		return pictures;
	}

	public Notes getNotes() {
		return notes;
	}

	public Tags getTags() {
		return tags;
	}

	public Set<User> getOwners() {
		Set<User> users = new HashSet<User>();
		for (CollectionObject owner : owners) {
			users.add(owner.getOwner());
		}
		return users;
	}

	public Picture getMainPicture() {
		return pictures.first();
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public int getOwnersCount() {
		return owners.size();
	}

	public int getWishersCount() {
		return wishList.size();
	}

	public Reviews getReviews() {
		return reviews;
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public void addComment(Comment comment) {
		comments.add(comment);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Comments getComments() {
		return comments;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public Long getId() {
		return id;
	}

	public boolean isBuyable() {
		return getAvailableCopyCount() > 0;
	}

	public int getAvailableCopyCount() {
		return shopItems.getAvailableCopyCount();
	}

	public ShopItems getShopItems() {
		return shopItems;
	}

}
