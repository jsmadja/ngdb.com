package com.ngdb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreUpdate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ShopItem {

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Column(name = "modification_date", nullable = false)
	private Date modificationDate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Embedded
	private Picture mainPicture;

	@OneToOne(optional = false)
	private State state;

	private double price;

	@ManyToOne(optional = false)
	private Article article;

	@ManyToOne(optional = false)
	private User user;

	private String details;

	private Boolean sold;

	public ShopItem() {
		this.creationDate = this.modificationDate = new Date();
	}

	@PreUpdate
	public void update() {
		this.modificationDate = new Date();
	}

	public ShopItem(Picture mainPicture, Article article, double price, State state, String details, User user) {
		this();
		this.mainPicture = mainPicture;
		this.article = article;
		this.price = price;
		this.state = state;
		this.details = details;
		this.user = user;
	}

	public double getPrice() {
		return price;
	}

	public String getTitle() {
		return article.getTitle();
	}

	public Picture getMainPicture() {
		return mainPicture;
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

	public void addPicture(Picture picture) {
		mainPicture = picture;
	}

	public User getUser() {
		return user;
	}

	public boolean isSold() {
		return sold;
	}

	public Long getId() {
		return id;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

}
