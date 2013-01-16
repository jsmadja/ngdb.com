package com.ngdb.web.pages.shop;

import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.model.CustomCurrenciesList;
import com.ngdb.web.model.StateList;
import com.ngdb.web.pages.Market;
import com.ngdb.web.services.infrastructure.CurrencyService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiresUser
public class ShopItemUpdate {

    @Inject
    private Session session;

    @Persist
    @Property
    @Validate("required")
    private Double priceInCustomCurrency;

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
    @Validate("required")
    private State state;

    @Persist
    @Property
    private String customCurrency;

    @Property
    @Persist("entity")
    private ShopItem shopItem;

    @Inject
    private CurrencyService currencyService;

    @Inject
    private Request request;

    @Persist
    @Property
    private List<UploadedFile> pictures;

    @Persist
    @Property
    private Set<Picture> storedPictures;

    @Property
    private Picture picture;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    @Inject
    private CurrentUser currentUser;

    boolean onActivate(ShopItem shopItem) {
        this.shopItem = shopItem;
        this.details = shopItem.getDetails();
        this.state = shopItem.getState();
        this.shopItem.updateModificationDate();
        this.storedPictures = shopItem.getPictures().all();
        this.customCurrency = shopItem.getCustomCurrency();
        this.priceInCustomCurrency = shopItem.getPriceInCustomCurrency();
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
    @DiscardAfter
    public Object onSuccess() {
        shopItem.setDetails(details);
        shopItem.setState(state);
        shopItem.setPriceInCustomCurrency(priceInCustomCurrency);
        shopItem.setCustomCurrency(customCurrency);
        shopItem = (ShopItem) session.merge(shopItem);
        for (UploadedFile uploadedPicture : pictures) {
            Picture picture = pictureService.store(uploadedPicture, shopItem);
            shopItem.addPicture(picture);
            session.merge(picture);
        }
        return pageRenderLinkSource.createPageRenderLinkWithContext(Market.class, "byUser", currentUser.getUser());
    }

    public SelectModel getStates() {
        return new StateList(referenceService.getStates());
    }

    public SelectModel getCustomCurrencies() {
        Collection<String> currencies = currencyService.allCurrencies();
        return new CustomCurrenciesList(currencies);
    }

    @CommitAfter
    Object onActionFromDeletePicture(Picture picture) {
        shopItem.removePicture(picture);
        pictureService.invalidateCoverOf(shopItem);
        pictureService.delete(picture);
        this.storedPictures = shopItem.getPictures().all();
        return this;
    }

    public String getSmallPictureUrl() {
        return picture.getUrl("small");
    }

    ShopItem onPassivate() {
        return shopItem;
    }

}
