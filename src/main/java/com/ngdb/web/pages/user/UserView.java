package com.ngdb.web.pages.user;

import java.util.Set;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Market;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.infrastructure.CurrentUser;

public class UserView {

	@Persist
	private User user;

	@Property
	private Wish wish;

	@Property
	private Article article;

	@Property
	private ShopItem shopItem;

	@Property
	private User seller;

	@Inject
	private CurrentUser currentUser;

	@Inject
	private Session session;

	@Inject
	private Market market;

	void onActivate(User user) {
		this.user = user;
	}

	public String getViewPage() {
		if (wish.getArticle().getType().equals(Game.class)) {
			return "article/game/gameView";
		}
		return "article/hardware/hardwareView";
	}

	public User getUser() {
		return user;
	}

	public boolean isBuyable() {
		return currentUser.canBuy(getShopItemFromDb());
	}

	private ShopItem getShopItemFromDb() {
		return (ShopItem) session.load(ShopItem.class, shopItem.getId());
	}

	public Set<User> getSellers() {
		return market.findSellersOf(wish.getArticle());
	}

}
