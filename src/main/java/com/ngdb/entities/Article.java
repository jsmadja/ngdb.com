package com.ngdb.entities;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

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

	@OneToMany(mappedBy = "article")
	private Set<Note> notes;

	@ManyToMany
	@JoinTable(name = "ArticleTags", inverseJoinColumns = { @JoinColumn(name = "tag_id") }, joinColumns = { @JoinColumn(name = "article_id") })
	private Set<Tag> tags = new HashSet<Tag>();

	@ElementCollection
	@JoinTable(name = "ArticlePictures", joinColumns = { @JoinColumn(name = "article_id") })
	private Set<Picture> pictures;

	@OneToMany(mappedBy = "article")
	private Set<Review> reviews;

	@OneToMany(mappedBy = "article")
	private Set<Comment> comments;

	@OneToMany(mappedBy = "article")
	private Set<Wish> wishList;

	@OneToMany(mappedBy = "article")
	private Set<ShopItem> shopItems;

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

	public void addProperty(Note property) {
		notes.add(property);
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addPicture(Picture picture) {
		pictures.add(picture);
	}

	public Set<Picture> getPictures() {
		return Collections.unmodifiableSet(pictures);
	}

	public Set<Note> getNotes() {
		return Collections.unmodifiableSet(notes);
	}

	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	public Set<User> getOwners() {
		Set<User> users = new HashSet<User>();
		for (CollectionObject owner : owners) {
			users.add(owner.getUser());
		}
		return users;
	}

	public void addOwner(CollectionObject collection) {
		owners.add(collection);
	}

	public void addWisher(User user) {
		// user.addToWishList(this);
		// wishers.add(user);
	}

	public Picture getMainPicture() {
		return (pictures == null || pictures.isEmpty()) ? Picture.EMPTY : pictures.iterator().next();
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
		return 0;
		// return wishers.size();
	}

	public String getCollectionRank() {
		return "1st";
	}

	public String getWishRank() {
		return "1st";
	}

	public Set<Review> getReviews() {
		return Collections.unmodifiableSet(reviews);
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

	public Set<Comment> getComments() {
		return Collections.unmodifiableSet(comments);
	}

	public int getAvailableCopyCount() {
		return shopItems.size();
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

}
