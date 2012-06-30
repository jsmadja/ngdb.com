package com.ngdb.web.pages.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.hibernate.Session;

import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.model.StateList;
import com.ngdb.web.services.infrastructure.CurrencyService;
import com.ngdb.web.services.infrastructure.PictureService;

@RequiresAuthentication
public class ShopItemUpdate {

	@Inject
	private Session session;

	@Persist
	@Property
	@Validate("required")
	private Double priceInDollars;

	@Persist
	@Property
	@Validate("required")
	private Double priceInEuros;

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

	@Inject
	private CurrencyService currencyService;

	@Inject
	private Request request;

	@Inject
	private ComponentResources componentResources;

	@InjectComponent
	private Zone priceZone;

	@Property
	private String suggestedPriceInEuros;

	@Property
	private String suggestedPriceInDollars;

	@Persist
	@Property
	private List<UploadedFile> pictures;

	@Persist
	@Property
	private Set<Picture> storedPictures;

	@Property
	private Picture picture;

	boolean onActivate(ShopItem shopItem) {
		this.shopItem = shopItem;
		this.details = shopItem.getDetails();
		this.priceInDollars = shopItem.getPriceInDollars();
		this.priceInEuros = shopItem.getPriceInEuros();
		this.state = shopItem.getState();
		this.shopItem.updateModificationDate();
		this.storedPictures = shopItem.getPictures().all();
		if (pictures == null) {
			pictures = new ArrayList<UploadedFile>();
		}
		return true;
	}

	@OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
	void onImageUpload(UploadedFile uploadedFile) {
		if (uploadedFile != null) {
			this.pictures.add(uploadedFile);
		}
	}

	@CommitAfter
	public Object onSuccess() {
		shopItem.setDetails(details);
		shopItem.setPriceInDollars(priceInDollars);
		shopItem.setPriceInEuros(priceInEuros);
		shopItem.setState(state);
		shopItem = (ShopItem) session.merge(shopItem);
		for (UploadedFile uploadedPicture : pictures) {
			Picture picture = pictureService.store(uploadedPicture, shopItem);
			shopItem.addPicture(picture);
			session.merge(picture);
		}
		shopItemView.setShopItem(shopItem);
		return shopItemView;
	}

	public SelectModel getStates() {
		return new StateList(referenceService.getStates());
	}

	public Object onDollarsChanged() {
		String priceToConvert = request.getParameter("param");
		priceToConvert = priceToConvert.replace(',', '.');
		if (priceToConvert != null) {
			priceInDollars = Double.valueOf(priceToConvert);
			suggestedPriceInEuros = "Suggested EUR price ~ " + currencyService.fromDollarsToEuros(priceInDollars) + " €";
		}
		return request.isXHR() ? priceZone.getBody() : null;
	}

	public Object onEurosChanged() {
		String priceToConvert = request.getParameter("param");
		priceToConvert = priceToConvert.replace(',', '.');
		if (priceToConvert != null) {
			priceInEuros = Double.valueOf(priceToConvert);
			suggestedPriceInDollars = "Suggested USD price ~ $" + currencyService.fromEurosToDollars(priceInEuros);
		}
		return request.isXHR() ? priceZone.getBody() : null;
	}

	@CommitAfter
	Object onActionFromDeletePicture(Picture picture) {
		shopItem.removePicture(picture);
		pictureService.delete(picture);
		this.storedPictures = shopItem.getPictures().all();
		return this;
	}

	public String getSmallPictureUrl() {
		return picture.getUrl("small");
	}

}
