package com.ngdb.web.components.common.layout;

import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ngdb.entities.GameFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Comment;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.Category;
import com.ngdb.web.pages.Market;
import com.ngdb.web.pages.article.game.GameView;
import com.ngdb.web.pages.article.hardware.HardwareView;
import com.ngdb.web.pages.shop.ShopItemView;

public class Events {

	@Inject
	private Session session;

	@Property
	private Comment comment;

	@Property
	private Game release;

	@Property
	private List<ShopItem> shopItems;

	@Property
	private ShopItem shopItem;

	@Property
	private Long forSaleCount;

	@InjectPage
	private GameView gameView;

	@InjectPage
	private HardwareView hardwareView;

	@Inject
	private GameFactory gameFactory;

	@InjectPage
	private Market marketPage;

	@InjectPage
	private ShopItemView shopItemView;

	@Inject
	private com.ngdb.entities.Market market;

	@SetupRender
	void onInit() {
		this.shopItems = market.findLastForSaleItems();
		this.forSaleCount = market.getNumForSaleItems();
	}

	@SuppressWarnings("unchecked")
	public Collection<Comment> getLastComments() {
		return session.createCriteria(Comment.class).addOrder(Order.desc("creationDate")).setMaxResults(3).list();
	}

	public Collection<Game> getReleases() {
		return gameFactory.findAllByReleasedToday();
	}

	Object onActionFromComment(Article article) {
		if (article instanceof Game) {
			gameView.setGame((Game) article);
			return gameView;
		}
		hardwareView.setHardware((Hardware) article);
		return hardwareView;
	}

	Object onActionFromMore() {
		marketPage.setCategory(Category.none);
		return marketPage;
	}

	Object onActionFromShopItem(ShopItem shopItem) {
		shopItemView.setShopItem(shopItem);
		return shopItemView;
	}

}
