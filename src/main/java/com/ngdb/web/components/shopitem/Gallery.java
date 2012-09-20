package com.ngdb.web.components.shopitem;

import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.ShopItemPictures;
import com.ngdb.entities.shop.ShopItem;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.json.JSONObject;

public class Gallery {

	@Property
	@Parameter(allowNull = false)
	private ShopItem shopItem;

	@Property
	private Picture picture;

	public JSONObject getParams() {
		return new JSONObject("maxHeight", "600px");
	}

	public ShopItemPictures getPictures() {
		return shopItem.getPictures();
	}

	public String getSmallPictureUrl() {
		return picture.getUrl("small");
	}

}
