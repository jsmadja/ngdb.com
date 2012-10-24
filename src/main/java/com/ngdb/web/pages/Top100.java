package com.ngdb.web.pages;

import com.ngdb.entities.*;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.Filter;
import com.ngdb.web.model.Top100List;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.joda.time.DateMidnight;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

public class Top100 {

    @Inject
    private Charts charts;

    @Property
    private Top100Item topItem;

    @Property
    private Top100ShopItem topShopItem;

    @Persist
    @Property
    private String top100;

    @Persist
    private String currentTop100;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone top100Zone;

    @Inject
    private Messages messages;

    @Inject
    private BeanModelSource beanModelSource;

    private final Collection<String> tops = new ArrayList<String>();

    @Inject
    private com.ngdb.entities.Market market;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private Population population;

    @SetupRender
    void init() {
        tops.clear();
        List<Platform> platforms = referenceService.getPlatforms();

        for (Platform platform : platforms) {
            if(market.getNumGamesSold(platform) > 0) {
                tops.add("Recently sold - "+platform.getShortName());
            }
        }
        tops.add("-");
        for (Platform platform : platforms) {
            if(market.getNumGamesForSale(platform) > 0) {
                tops.add("Recently in shop - "+platform.getShortName());
            }
        }
        tops.add("-");
        tops.add("Rating");
        tops.add("-");
        for (Platform platform : platforms) {
            tops.add("Collection - "+platform.getShortName());
        }
        tops.add("-");
        for (Platform platform : platforms) {
            tops.add("Wishlist - "+platform.getShortName());
        }
        if(currentTop100 == null) {
            currentTop100 = tops.iterator().next();
        }
    }

    @OnEvent(component = "top100", value = EventConstants.VALUE_CHANGED)
    public void onSelectFromTop100(String currentTop100) {
        if(isNotSeparator(currentTop100)) {
            this.currentTop100 = currentTop100;
            ajaxResponseRenderer.addRender(top100Zone);
        }
    }

    public BeanModel<Top100Item> getArticleModel() {
        BeanModel<Top100Item> model = beanModelSource.createDisplayModel(Top100Item.class, messages);
        model.get("title").label(messages.get("common.Title")).sortable(true);
        model.get("originTitle").label(messages.get("common.Origin")).sortable(true);
        model.get("rank").label(messages.get("common.Rank")).sortable(true);
        model.get("count").label(messages.get("common.Count")).sortable(true);
        model.include("rank", "title", "count", "originTitle");
        model.reorder("rank", "count", "title", "originTitle");
        if(isRatingTop100()) {
            model.get("count").label(messages.get("common.Mark"));
            model.exclude("originTitle");
        }
        return model;
    }

    public BeanModel<Top100ShopItem> getShopItemModel() {
        BeanModel<Top100ShopItem> model = beanModelSource.createDisplayModel(Top100ShopItem.class, messages);
        model.get("title").label(messages.get("common.Title")).sortable(true);
        model.get("originTitle").label(messages.get("common.Origin")).sortable(true);
        model.get("price").label(messages.get("common.Price"));
        model.get("sellerId").label(messages.get("common.Seller"));
        model.get("state").label(messages.get("common.State"));
        if(isRecentlySoldTop100()) {
            model.get("saleDate").label(messages.get("common.SaleDate"));
        } else {
            model.get("saleDate").label(messages.get("common.ForSaleDate"));
        }
        model.include("saleDate", "price", "title", "originTitle", "sellerId", "state");
        model.reorder("saleDate", "price", "title", "originTitle", "state", "sellerId");
        return model;
    }

    public String getSaleDateFormatted() {
        Date saleDate = topShopItem.getSaleDate();
        return DateTimeFormat.forPattern("dd/MM/yyyy").withLocale(currentUser.getLocale()).print(saleDate.getTime());
    }

    @Inject
    private CurrentUser currentUser;

    public Collection<Top100Item> getTopItems() {
        Platform platform = getPlatform();
        return listGames(platform);
    }

    private Platform getPlatform() {
        Platform platform = null;
        if(currentTop100.contains("-")) {
            String platformName = currentTop100.split("-")[1].trim();
            platform = referenceService.findPlatformByName(platformName);
        }
        return platform;
    }

    public Collection<Top100ShopItem> getTopShopItems() {
        Collection<Top100ShopItem> top100ShopItems = new ArrayList<Top100ShopItem>();
        Platform platform = getPlatform();
        if(isRecentlySoldTop100()) {
            top100ShopItems = charts.findTop100OfGamesRecentlySold(platform);
        } else if(isRecentlyInShopTop100()) {
            top100ShopItems = charts.findTop100OfGamesRecentlyInShop(platform);
        }
        return top100ShopItems;
    }

    private Collection<Top100Item> listGames(Platform platform) {
        if(isCollectionTop100()) {
            return charts.findTop100OfGamesInCollection(platform);
        } else if(isWishlistTop100()) {
            return charts.findTop100OfGamesInWishlist(platform);
        } else if(isRatingTop100()) {
            return charts.findTop100OfGamesWithRating();
        }
        throw new IllegalStateException("Invalid Top100 selection");
    }

    public String getCount() {
        if(isRecentlyInShopTop100()) {
            return market.getLastShopItemForSaleOf(topItem.getId()).getPriceAsString();
        }
        return topItem.getCount();
    }

    private boolean isRecentlyInShopTop100() {
        return currentTop100.contains("Recently in shop");
    }

    private boolean isRecentlySoldTop100() {
        return currentTop100.contains("Recently sold");
    }

    private boolean isRatingTop100() {
        return currentTop100.contains("Rating");
    }

    private boolean isWishlistTop100() {
        return currentTop100.contains("Wishlist");
    }

    private boolean isCollectionTop100() {
        return currentTop100.contains("Collection");
    }

    public SelectModel getTop100List() {
        return new Top100List(tops);
    }

    private boolean isNotSeparator(String currentTop100) {
        return !"-".equals(currentTop100);
    }

    public boolean isShopItemTop() {
        return isRecentlyInShopTop100() || isRecentlySoldTop100();
    }

    public String getByArticle() {
        return Filter.byArticle.name();
    }

    public String getSeller() {
        Long sellerId = topShopItem.getSellerId();
        return population.getNameOf(sellerId);
    }

}