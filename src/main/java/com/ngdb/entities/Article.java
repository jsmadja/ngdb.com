package com.ngdb.entities;

import java.util.Collection;
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
import javax.persistence.Transient;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

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
		if (pictures == null) {
			pictures = new HashSet<Picture>();
		}
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
			users.add(owner.getOwner());
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

	public int getSealedQuantity() {
		return getSealedItems().size();
	}

	private Collection<ShopItem> getSealedItems() {
		return Collections2.filter(shopItems, keepSealedState);
	}

	public int getMintQuantity() {
		return getMintItems().size();
	}

	private Collection<ShopItem> getMintItems() {
		return Collections2.filter(shopItems, keepMintState);
	}

	public int getUsedQuantity() {
		return getUsedItems().size();
	}

	private Collection<ShopItem> getUsedItems() {
		return Collections2.filter(shopItems, keepUsedState);
	}

	public String getSealedAverage() {
		return getAverage(getSealedItems());
	}

	public String getSealedMax() {
		return getMax(getSealedItems());
	}

	public String getSealedMin() {
		return getMin(getSealedItems());
	}

	private String getMax(Collection<ShopItem> items) {
		Double max = Double.MIN_VALUE;
		for (ShopItem shopItem : items) {
			max = Math.max(max, shopItem.getPrice());
		}
		if (max.equals(Double.MIN_VALUE)) {
			return "";
		}
		return "$" + max;
	}

	private String getMin(Collection<ShopItem> items) {
		Double min = Double.MAX_VALUE;
		for (ShopItem shopItem : items) {
			min = Math.min(min, shopItem.getPrice());
		}
		if (min.equals(Double.MAX_VALUE)) {
			return "";
		}
		return "$" + min;
	}

	private String getAverage(Collection<ShopItem> items) {
		Double average = 0D;
		for (ShopItem shopItem : items) {
			average += shopItem.getPrice();
		}
		if (average.equals(0D)) {
			return "";
		}
		return "$" + (average / shopItems.size());
	}

	public String getMintAverage() {
		return getAverage(getMintItems());
	}

	public String getMintMax() {
		return getMax(getMintItems());
	}

	public String getMintMin() {
		return getMin(getMintItems());
	}

	public String getUsedAverage() {
		return getAverage(getUsedItems());
	}

	public String getUsedMax() {
		return getMax(getUsedItems());
	}

	public String getUsedMin() {
		return getMin(getUsedItems());
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

	@Transient
	private StateFilter keepSealedState = new StateFilter("Sealed");

	@Transient
	private StateFilter keepMintState = new StateFilter("Mint");

	@Transient
	private StateFilter keepUsedState = new StateFilter("Used");

	class StateFilter implements Predicate<ShopItem> {
		private String state;

		public StateFilter(String state) {
			this.state = state;
		}

		@Override
		public boolean apply(ShopItem shopItem) {
			if (shopItem == null) {
				return false;
			}
			return shopItem.isSold() && state.equals(shopItem.getState().getTitle());
		}
	}

}
