package com.ngdb.web.components.common.layout;

import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Projections.countDistinct;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.Game;
import com.ngdb.entities.Hardware;
import com.ngdb.entities.ShopItem;
import com.ngdb.entities.User;
import com.ngdb.entities.Wish;
import com.ngdb.web.Category;
import com.ngdb.web.pages.ShopView;

public class Menu {

	@Property
	private List<ShopItem> shopItems;

	@Property
	private ShopItem shopItem;

	@Property
	private Long gameCount;

	@Property
	private Long hardwareCount;

	@Property
	private Long wishListCount;

	@Property
	private Long userCount;

	@Inject
	private Session session;

	@Property
	private Long forSaleCount;

	@Property
	private Long soldCount;

	@InjectPage
	private ShopView shopView;

	@SetupRender
	public void init() {
		this.shopItems = session.createQuery("SELECT si FROM ShopItem si ORDER BY modificationDate DESC").setMaxResults(3).list();
		this.gameCount = (Long) session.createCriteria(Game.class).setProjection(count("id")).uniqueResult();
		this.userCount = (Long) session.createCriteria(User.class).setProjection(count("id")).uniqueResult();
		this.hardwareCount = (Long) session.createCriteria(Hardware.class).setProjection(count("id")).uniqueResult();
		this.wishListCount = (Long) session.createCriteria(Wish.class).setProjection(countDistinct("wisher")).uniqueResult();
		this.forSaleCount = (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", false)).uniqueResult();
		this.soldCount = (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", true)).uniqueResult();
	}

	public String getBySoldDate() {
		return "bySoldDate";
	}

	Object onActionFromShopForSale() {
		shopView.setCategory(Category.none);
		return shopView;
	}

	Object onActionFromMore() {
		shopView.setCategory(Category.none);
		return shopView;
	}

	Object onActionFromShopSold() {
		shopView.setCategory(Category.bySoldDate);
		return shopView;
	}

}
