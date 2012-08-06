package com.ngdb.entities.article;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.ocpsoft.pretty.time.PrettyTime;

import com.google.common.base.Objects;
import com.ngdb.entities.article.element.ArticlePictures;
import com.ngdb.entities.article.element.Comments;
import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Notes;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.Review;
import com.ngdb.entities.article.element.Reviews;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.article.element.Tags;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.ShopItems;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement(name = "article")
@XmlAccessorType(FIELD)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public abstract class Article implements Comparable<Article>, Serializable {

	private static final int MAX_DETAIL_LENGTH = 1024;

	@XmlTransient
	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@XmlTransient
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

	@OneToOne(fetch = FetchType.LAZY)
	private Origin origin;

	@Embedded
	@XmlTransient
	private Notes notes;

	@Embedded
	@XmlTransient
	private Tags tags;

	@Embedded
	@XmlTransient
	private ArticlePictures pictures = new ArticlePictures();

	@Embedded
	@XmlTransient
	private Reviews reviews;

	@Embedded
	@XmlTransient
	private Comments comments;

	@XmlTransient
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<Wish> wishList;

	@Embedded
	@XmlTransient
	private ShopItems shopItems;

	@XmlTransient
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<CollectionObject> owners;

	private String details;

	@OneToOne(fetch = FetchType.LAZY)
	private Platform platform;

	@OneToOne(fetch = FetchType.LAZY)
	private Publisher publisher;

	public Article() {
		creationDate = modificationDate = new Date();
	}

	public void updateModificationDate() {
		modificationDate = new Date();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Article) {
			Article a = (Article) obj;
			if (id.equals(a.id)) {
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

	public void addPicture(Picture picture) {
		if (pictures == null) {
			pictures = new ArticlePictures();
		}
		pictures.add(picture);
	}

	public ArticlePictures getPictures() {
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
		details = StringUtils.defaultString(details);
		int end = details.length() < MAX_DETAIL_LENGTH ? details.length() : MAX_DETAIL_LENGTH;
		this.details = details.substring(0, end);
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

	@Override
	public int compareTo(Article article) {
		return title.compareToIgnoreCase(StringUtils.defaultString(article.title));
	}

	@Override
	public String toString() {
		return title;
	}

	public Collection<ShopItem> getShopItemsForSale() {
		return shopItems.getShopItemsForSale();
	}

	public abstract Class<?> getType();

	public boolean hasShopItemInState(State state) {
		return shopItems.hasShopItemInState(state);
	}

	public int getAvailableCopyInState(State state) {
		return shopItems.getAvailableCopyInState(state);
	}

	public double getAveragePriceInState(State state) {
		return shopItems.getAveragePriceInState(state);
	}

	public double getMaxPriceInState(State state) {
		return shopItems.getMaxPriceInState(state);
	}

	public double getMinPriceInState(State state) {
		return shopItems.getMinPriceInState(state);
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Platform getPlatform() {
		return platform;
	}

	public String getLastUpdateDate() {
		return new PrettyTime(Locale.UK).format(modificationDate);
	}

	public boolean containsTag(String tag) {
		return tags.contains(tag);
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addReview(Review review) {
		reviews.add(review);
	}

	public void removePicture(Picture picture) {
		pictures.remove(picture);
	}

	public boolean containsTag(Tag tag) {
		return tags.contains(tag);
	}

	public boolean containsProperty(String name) {
		return notes.contains(name);
	}

	public void addNote(Note note) {
		notes.add(note);
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

}
