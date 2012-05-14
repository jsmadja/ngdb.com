package com.ngdb.domain;

import java.util.Date;

public class ShopItem implements BaseEntity {

	private Date creationDate;
	private Long id;
	private Picture mainPicture;
	private double price;
	private Game game;

	private static long ID = 0;

	public ShopItem(Picture mainPicture, Game game, double price) {
		this.id = ID++;
		this.creationDate = new Date();
		this.mainPicture = mainPicture;
		this.game = game;
		this.price = price;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	public double getPrice() {
		return price;
	}

	public String getTitle() {
		return game.getTitle() + " (" + game.getPlatform().name() + " - " + game.getOrigin().getTitle() + ")";
	}

	public Picture getMainPicture() {
		return mainPicture;
	}

}
