package com.ngdb.web.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

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

	@Persist
	private Long id;

	@Property
	private String username;

	@Inject
	private Session session;

	void onActivate(String filter, String value) {
		if (isNotBlank(filter)) {
			this.category = Category.valueOf(Category.class, filter);
			if (StringUtils.isNumeric(value)) {
				this.id = Long.valueOf(value);
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
				this.shopItems = article.getShopItemsForSale();
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
				this.shopItems = user.getShopItemsForSale();
				this.username = user.getLogin();
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

	@CommitAfter
	Object onActionFromSold(ShopItem shopItem) {
		shopItem.sold();
		return this;
	}

	@CommitAfter
	Object onActionFromRemove(ShopItem shopItem) {
		session.delete(shopItem);
		return this;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return currentUser.getUser();
	}

	public boolean getCanMarkAsSold() {
		return currentUser.canMarkAsSold(shopItem);
	}

	public boolean getCanRemove() {
		return currentUser.canRemove(shopItem);
	}

	public boolean getCanEdit() {
		return currentUser.canEdit(shopItem);
	}

}
