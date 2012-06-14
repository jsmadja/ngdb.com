package com.ngdb.entities.shop;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.ShopItemPictures;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.user.User;

@Entity
public class ShopItem {

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "modification_date", nullable = false)
	private Date modificationDate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Embedded
	private ShopItemPictures pictures = new ShopItemPictures();

	@OneToOne
	private State state;

	private double price;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Article article;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private User seller;

	private String details;

	private Boolean sold = false;

	private String currency = "$";

	@Embedded
	private PotentialBuyers potentialBuyers;

	public ShopItem() {
		this.creationDate = this.modificationDate = new Date();
	}

	@PreUpdate
	public void update() {
		this.modificationDate = new Date();
	}

	public double getPrice() {
		return price;
	}

	public String getTitle() {
		return article.getTitle();
	}

	public State getState() {
		return state;
	}

	public Article getArticle() {
		return article;
	}

	public String getDetails() {
		return details;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public User getSeller() {
		return seller;
	}

	public boolean isSold() {
		return sold == null ? false : sold;
	}

	public Long getId() {
		return id;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public void addPicture(Picture picture) {
		pictures.add(picture);
	}

	public ShopItemPictures getPictures() {
		return pictures;
	}

	public Picture getMainPicture() {
		return pictures.first();
	}

	public void addPotentialBuyer(User potentialBuyer) {
		potentialBuyers.add(potentialBuyer);
	}

	public boolean isNotAlreadyWantedBy(User potentialBuyer) {
		return !potentialBuyers.contains(potentialBuyer);
	}

	public void sold() {
		this.sold = true;
	}

}
