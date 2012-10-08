package com.ngdb.web.pages.shop;

import com.ngdb.entities.article.Article;
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
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.DiscardAfter;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
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

@RequiresUser
public class ShopItemCreate {

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

    @Persist
    @Property
    private Article article;

    @Inject
    private PictureService pictureService;

    @Inject
    private CurrentUser currentUser;

    @Persist
    @Property
    @Validate("required")
    private State state;

    @Persist
    @Property
    private String customCurrency;

    @Inject
    private CurrencyService currencyService;

    @Inject
    private Request request;

    @Inject
    private ComponentResources componentResources;

    @Persist
    @Property
    private List<UploadedFile> pictures;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    @Inject
    private com.ngdb.entities.Market market;

    boolean onActivate(Article article) {
        this.article = article;
        this.state = referenceService.findStateByTitle("Used");
        if(currentUser.isAnonymous()) {
            this.customCurrency = "USD";
        } else {
            this.customCurrency = currentUser.getPreferedCurrency();
        }
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
        ShopItem shopItem = new ShopItem();
        shopItem.setArticle(article);
        shopItem.setDetails(details);
        shopItem.setSeller(currentUser.getUser());
        shopItem.setState(state);
        shopItem.setPriceInCustomCurrency(priceInCustomCurrency);
        shopItem.setCustomCurrency(customCurrency);
        shopItem = (ShopItem) session.merge(shopItem);
        for (UploadedFile uploadedPicture : pictures) {
            Picture picture = pictureService.store(uploadedPicture, shopItem);
            shopItem.addPicture(picture);
            session.merge(picture);
        }
        market.tellWishers(shopItem);
        return pageRenderLinkSource.createPageRenderLinkWithContext(Market.class, "byUser", currentUser.getUser());
    }

    public SelectModel getStates() {
        return new StateList(referenceService.getStates());
    }

    public SelectModel getCustomCurrencies() {
        Collection<String> currencies = currencyService.allCurrencies();
        return new CustomCurrenciesList(currencies);
    }

}
