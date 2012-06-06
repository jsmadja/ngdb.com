package com.ngdb.entities.article;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.common.base.Objects;
import com.ngdb.entities.article.element.ArticlePictures;
import com.ngdb.entities.article.element.Comments;
import com.ngdb.entities.article.element.Notes;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.Reviews;
import com.ngdb.entities.article.element.Tags;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.ShopItems;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement(name = "article")
@XmlAccessorType(FIELD)
public abstract class Article implements Comparable<Article> {

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

	@OneToOne
	private Origin origin;

	@Embedded
	@XmlTransient
	private Notes notes;

	@Embedded
	@XmlTransient
	private Tags tags;

	@Embedded
	private ArticlePictures pictures;

	@Embedded
	@XmlTransient
	private Reviews reviews;

	@Embedded
	@XmlTransient
	private Comments comments;

	@XmlTransient
	@OneToMany(mappedBy = "article")
	private Set<Wish> wishList;

	@Embedded
	@XmlTransient
	private ShopItems shopItems;

	@XmlTransient
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
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
		return title.toLowerCase().compareTo(article.title.toLowerCase());
	}

	@Override
	public String toString() {
		return title;
	}

	public Collection<ShopItem> getShopItemsForSale() {
		return shopItems.getShopItemsForSale();
	}

	public abstract Class<?> getType();

}
