package com.ngdb.entities.shop;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.article.element.ShopItemPictures;
import com.ngdb.entities.reference.State;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrencyService;
import com.ngdb.web.services.infrastructure.UnavailableRatingException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import static java.text.MessageFormat.format;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.joda.money.CurrencyUnit.of;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopItem implements Comparable<ShopItem>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @ManyToOne(optional = false, fetch = LAZY)
    private User seller;

    private static final int MAX_DETAIL_LENGTH = 1024;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    @Embedded
    private ShopItemPictures pictures;

    @OneToOne
    private State state;

    @ManyToOne(optional = false, fetch = LAZY)
    private Article article;

    @ManyToOne(optional = true, fetch = LAZY)
    private ShopOrder order;

    private String details;

    private Boolean sold = false;

    private Double priceInCustomCurrency;

    private String customCurrency;

    @Transient
    private CurrencyService currencyService;

    @Embedded
    private PotentialBuyers potentialBuyers;

    public ShopItem() {
        this.creationDate = this.modificationDate = new Date();
    }

    @PreUpdate
    public void update() {
        this.modificationDate = new Date();
    }

    public String getTitle() {
        return article.getTitle();
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

    public User getSeller() {
        return seller;
    }

    public boolean isSold() {
        return sold == null ? false : sold;
    }

    public Long getId() {
        return id;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setDetails(String details) {
        details = StringUtils.defaultString(details);
        int end = details.length() < MAX_DETAIL_LENGTH ? details.length() : MAX_DETAIL_LENGTH;
        this.details = details.substring(0, end);
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void addPicture(Picture picture) {
        pictures.add(picture);
    }

    public ShopItemPictures getPictures() {
        return pictures;
    }

    public Picture getMainPicture() {
        if (hasNoPicture()) {
            return article.getCover();
        }
        return pictures.first();
    }

    public void addPotentialBuyer(User potentialBuyer) {
        potentialBuyers.add(potentialBuyer);
    }

    public void removePotentialBuyer(User potentialBuyer) {
        potentialBuyers.remove(potentialBuyer);
    }

    public boolean isNotInBasketOf(User potentialBuyer) {
        return !potentialBuyers.contains(potentialBuyer);
    }

    public boolean isAlreadyWantedBy(User potentialBuyer) {
        return potentialBuyers.contains(potentialBuyer);
    }

    public void sold() {
        this.sold = true;
    }

    public boolean hasNoPicture() {
        return pictures == null || pictures.getCount() == 0;
    }

    public void updateModificationDate() {
        this.modificationDate = new Date();
    }

    @Override
    public String toString() {
        return format("{0} by {1} for {2} {3}", getArticle().getTitle(), seller.getLogin(), customCurrency, priceInCustomCurrency);
    }

    public String getPriceAsString() {
        return format("{0} {1}", priceInCustomCurrency, of(customCurrency).getSymbol());
    }

    public void removePicture(Picture picture) {
        pictures.remove(picture);
    }

    @Override
    public int compareTo(ShopItem shopItem) {
        if (shopItem == null || shopItem.article == null) {
            return 0;
        }
        return article.getTitle().compareToIgnoreCase(shopItem.article.getTitle());
    }

    public boolean hasCover() {
        return article.hasCover();
    }

    public Double getPriceInCustomCurrency() {
        return priceInCustomCurrency;
    }

    public void setPriceInCustomCurrency(Double priceInCustomCurrency) {
        this.priceInCustomCurrency = priceInCustomCurrency;
    }

    public String getCustomCurrency() {
        return customCurrency;
    }

    public void setCustomCurrency(String customCurrency) {
        this.customCurrency = customCurrency;
    }

    public Double getPriceIn(String currency) {
        if (currency.equalsIgnoreCase(customCurrency)) {
            return priceInCustomCurrency;
        }
        try {
            return currencyService.fromToRate(priceInCustomCurrency, customCurrency, currency);
        } catch (UnavailableRatingException e) {
            return priceInCustomCurrency;
        }
    }

    public String getPriceAsStringIn(String currency) {
        if (currency.equalsIgnoreCase(customCurrency)) {
            return priceInCustomCurrency + " " + customCurrency;
        }
        try {
            return currencyService.fromToRate(priceInCustomCurrency, customCurrency, currency) + " " + currency;
        } catch (UnavailableRatingException e) {
            return priceInCustomCurrency + " " + customCurrency;
        }
    }

    public void setOrder(ShopOrder shopOrder) {
        this.order = shopOrder;
    }

    public Collection<User> getWishers() {
        return article.getWishers();
    }

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public String getStars() {
        final int MAX_CONDITION = 9;
        int id = getState().getId().intValue();
        String conditionTitle = getState().getTitle();
        int condition = MAX_CONDITION - id + 1;
        String stars = "";

        stars += repeat(star(conditionTitle), condition);
        stars += repeat(greystar(conditionTitle), MAX_CONDITION - condition);

        return stars;
    }

    private String star(String conditionTitle) {
        return format("<img title='{0}' width='15px' src='/img/stars/star.png'>", conditionTitle);
    }

    private String greystar(String conditionTitle) {
        return format("<img title='{0}' width='15px' src='/img/stars/grey_star.png'>", conditionTitle);
    }

    public String getCustomCurrencyAsSymbol() {
        return CurrencyUnit.of(customCurrency).getSymbol();
    }
}
