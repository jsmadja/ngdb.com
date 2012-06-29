package com.ngdb.web.components.shopitem;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.ShopItemPictures;
import com.ngdb.entities.shop.ShopItem;

public class Gallery {

	@Property
	@Parameter(allowNull = false)
	private ShopItem shopItem;

	@Property
	private Picture picture;

	public ShopItemPictures getPictures() {
		return shopItem.getPictures();
	}

	public String getSmallPictureUrl() {
		return picture.getUrl("small");
	}

}
