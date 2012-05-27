package com.ngdb.entities;

import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;

public class Market {

	@Inject
	private Session session;

	public List<ShopItem> findAllItemsOf(Article article) {
		return allHardwares().add(eq("article", article)).add(eq("sold", false)).list();
	}

	public List<ShopItem> findAllItemsForSaleBy(User user) {
		return allHardwares().add(eq("seller", user)).add(eq("sold", false)).list();
	}

	public List<ShopItem> findAllItemsSold() {
		return allHardwares().add(eq("sold", true)).list();
	}

	public List<ShopItem> findAllItemsForSale() {
		return allHardwares().add(eq("sold", false)).list();
	}

	private Criteria allHardwares() {
		return session.createCriteria(ShopItem.class).addOrder(desc("modificationDate"));
	}

	public List<ShopItem> findLastForSaleItems() {
		return session.createQuery("SELECT si FROM ShopItem si WHERE si.sold = false ORDER BY modificationDate DESC").setMaxResults(3).list();
	}

	public Long getNumForSaleItems() {
		return (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", false)).uniqueResult();
	}

	public Long getNumSoldItems() {
		return (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", true)).uniqueResult();
	}

}
