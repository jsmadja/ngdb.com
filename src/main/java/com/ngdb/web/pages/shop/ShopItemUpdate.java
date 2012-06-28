package com.ngdb.web.pages.shop;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;
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

	@Property
	private UploadedFile mainPicture;

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

	boolean onActivate(ShopItem shopItem) {
		this.shopItem = shopItem;
		this.details = shopItem.getDetails();
		this.priceInDollars = shopItem.getPriceInDollars();
		this.priceInEuros = shopItem.getPriceInEuros();
		this.state = shopItem.getState();
		this.shopItem.updateModificationDate();
		return true;
	}

	@CommitAfter
	public Object onSuccess() {
		shopItem.setDetails(details);
		shopItem.setPriceInDollars(priceInDollars);
		shopItem.setPriceInEuros(priceInEuros);
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

	public Object onDollarsChanged() {
		String priceToConvert = request.getParameter("param");
		if (priceToConvert != null) {
			priceInDollars = Double.valueOf(priceToConvert);
			suggestedPriceInEuros = "Suggested EUR price ~ " + currencyService.fromDollarsToEuros(priceInDollars) + " €";
		}
		return request.isXHR() ? priceZone.getBody() : null;
	}

	public Object onEurosChanged() {
		String priceToConvert = request.getParameter("param");
		if (priceToConvert != null) {
			priceInEuros = Double.valueOf(priceToConvert);
			suggestedPriceInDollars = "Suggested USD price ~ $" + currencyService.fromEurosToDollars(priceInEuros);
		}
		return request.isXHR() ? priceZone.getBody() : null;
	}

}
