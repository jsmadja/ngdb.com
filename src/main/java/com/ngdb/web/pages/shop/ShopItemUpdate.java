package com.ngdb.web.pages.shop;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.model.CurrencyList;
import com.ngdb.web.model.StateList;
import com.ngdb.web.services.infrastructure.PictureService;

@RequiresAuthentication
public class ShopItemUpdate {

	@Property
	private UploadedFile mainPicture;

	@Inject
	private Session session;

	@Persist
	@Property
	private String currency;

	@Persist
	@Property
	@Validate("required")
	private Double price;

	@Persist
	@Property
	@Validate("required")
	private String details;

	@Inject
	private ReferenceService referenceService;

	@Inject
	private PictureService pictureService;

	@Persist
	@Property
	private State state;

	@Property
	@Persist("entity")
	private ShopItem shopItem;

	@InjectPage
	private ShopItemView shopItemView;

	boolean onActivate(ShopItem shopItem) {
		this.shopItem = shopItem;
		this.currency = shopItem.getCurrency();
		this.details = shopItem.getDetails();
		this.price = shopItem.getPrice();
		this.state = shopItem.getState();
		this.shopItem.updateModificationDate();
		return true;
	}

	@CommitAfter
	public Object onSuccess() {
		shopItem.setCurrency(currency);
		shopItem.setDetails(details);
		shopItem.setPrice(price);
		shopItem.setState(state);
		shopItem = (ShopItem) session.merge(shopItem);
		if (this.mainPicture != null) {
			Picture picture = pictureService.store(mainPicture, shopItem);
			shopItem.addPicture(picture);
			session.merge(picture);
		}
		shopItemView.setShopItem(shopItem);
		return shopItemView;
	}

	public SelectModel getStates() {
		return new StateList(referenceService.getStates());
	}

	public SelectModel getCurrencies() {
		return new CurrencyList(referenceService.getCurrencies());
	}

}
