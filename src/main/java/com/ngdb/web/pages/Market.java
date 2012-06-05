package com.ngdb.web.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Population;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.Category;
import com.ngdb.web.pages.shop.ShopItemView;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class Market {

	@Property
	private ShopItem shopItem;

	@Property
	private Collection<ShopItem> shopItems;

	@Inject
	private com.ngdb.entities.Market market;

	@Inject
	private ArticleFactory articleFactory;

	@Inject
	private Population population;

	@Persist
	private Category category;

	@InjectPage
	private ShopItemView shopItemView;

	@Inject
	private CurrentUser currentUser;

	private Long id;

	void onActivate(String category, String value) {
		if (isNotBlank(category)) {
			this.category = Category.valueOf(Category.class, category);
			if (StringUtils.isNumeric(value)) {
				id = Long.valueOf(value);
			}
		}
	}

	@SetupRender
	public void init() {
		if (category == null || category == Category.none) {
			this.shopItems = market.findAllItemsForSale();
		} else {
			switch (category) {
			case byArticle:
				Article article = articleFactory.findById(id);
				this.shopItems = market.findAllItemsOf(article);
				break;
			case bySoldDate:
				this.shopItems = market.findAllItemsSold();
				break;
			case byUser:
				User user;
				if (id == null) {
					user = currentUser.getUser();
				} else {
					user = population.findById(id);
				}
				this.shopItems = user.getShopItemsToSell();
				break;
			}
		}
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	Object onActionFromShopItem(ShopItem shopItem) {
		shopItemView.setShopItem(shopItem);
		return shopItemView;
	}

}
