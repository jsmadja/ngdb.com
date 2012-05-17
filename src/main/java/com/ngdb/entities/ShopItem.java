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

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ShopItem {

	@Column(name = "creation_date", nullable = false)
	private Date creationDate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Embedded
	private Picture mainPicture;

	private double price;

	@ManyToOne(optional = false)
	protected Article article;

	@ManyToOne(optional = false)
	protected User user;

	public ShopItem() {
	}

	public ShopItem(Picture mainPicture, Article article, double price) {
		this.mainPicture = mainPicture;
		this.article = article;
		this.price = price;
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

}
