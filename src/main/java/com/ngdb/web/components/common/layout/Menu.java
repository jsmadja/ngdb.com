package com.ngdb.web.components.common.layout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.web.pages.article.game.GameView;
import com.ngdb.web.pages.article.hardware.HardwareView;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.pages.shop.ShopItemView;

public class Menu {

	@Property
	private List<ShopItem> shopItems;

	@Property
	private ShopItem shopItem;

	@Property
	private Long forSaleCount;

	@InjectPage
	private ShopItemView shopItemView;

	@Inject
	private com.ngdb.entities.Market market;

    @InjectPage
    private GameView gameView;

    @InjectPage
    private HardwareView hardwareView;

    @Inject
    private ActionLogger actionLogger;

    @Property
    private ArticleAction update;

    @SetupRender
	void onInit() {
        this.shopItems = market.findRandomForSaleItems(6);
		this.forSaleCount = market.getNumForSaleItems();
	}

    public Collection<ArticleAction> getUpdates() {
        return actionLogger.listLastActions();
    }

	public String getPrice() {
		return market.getPriceOf(shopItem);
	}

}
