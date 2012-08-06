package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.ShopItemPredicates.isGameShopItem;
import static com.ngdb.ShopItemPredicates.isHardwareShopItem;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.*;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.infrastructure.CurrentUser;

import javax.annotation.Nullable;

public class Market {

	@Inject
	private Session session;

	@Inject
	private MailService mailService;

	@Inject
	@Symbol("host.url")
	private String hostUrl;

	@Inject
	private CurrentUser currentUser;

	public List<ShopItem> findAllItemsSold() {
		return allShopItems().add(eq("sold", true)).list();
	}

	public List<ShopItem> findAllItemsForSale() {
		return allShopItems().add(eq("sold", false)).list();
	}

	private Criteria allShopItems() {
		return session.createCriteria(ShopItem.class).addOrder(desc("modificationDate"));
	}

	public List<ShopItem> findLastForSaleItems(int count) {
        List<ShopItem> items = session.createQuery("SELECT si FROM ShopItem si WHERE si.sold = false").list();
        List<ShopItem> forSaleItems = new ArrayList<ShopItem>(Collections2.filter(items, new Predicate<ShopItem>() {
            @Override
            public boolean apply(@Nullable ShopItem input) {
                return input.hasCover();
            }
        }));
        List<ShopItem> randomItems = new ArrayList<ShopItem>();
        Set<Integer> ids = new HashSet<Integer>();
        while(ids.size() <count) {
            int randomIdx = RandomUtils.nextInt(forSaleItems.size());
            if(ids.add(randomIdx)) {
                randomItems.add(forSaleItems.get(randomIdx));
            }
        }
        return randomItems;
	}

	public Long getNumForSaleItems() {
		return (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", false)).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
	}

	public Long getNumSoldItems() {
		return (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", true)).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
	}

	public void potentialBuyer(ShopItem shopItem, User potentialBuyer) {
		shopItem.addPotentialBuyer(potentialBuyer);
		Map<String, String> params = new HashMap<String, String>();
		User seller = shopItem.getSeller();
		params.put("recipient", seller.getLogin());
		params.put("potentialBuyer", potentialBuyer.getLogin());
		params.put("shopItemTitle", shopItem.getTitle());
		params.put("shopItemUrl", hostUrl + "market.shopitem/" + shopItem.getId());
		params.put("potentialBuyerEmail", potentialBuyer.getEmail());
		mailService.sendMail(seller, "potential_buyer", params);
	}

	public void remove(ShopItem shopItem) {
		session.delete(shopItem);
	}

	public Collection<ShopItem> findAllByOriginAndPlatform(final Origin origin, final Platform platform) {
		Collection<ShopItem> list = allShopItems().list();
		list = Collections2.filter(list, new Predicate<ShopItem>() {
			@Override
			public boolean apply(ShopItem input) {
				Article article = input.getArticle();
				Origin articleOrigin = article.getOrigin();
				Platform articlePlatform = article.getPlatform();
				return articleOrigin.equals(origin) && articlePlatform.equals(platform);
			}
		});
		return list;
	}

	public String getPriceOf(com.ngdb.entities.shop.ShopItem shopItem) {
		if (currentUser.isFrench()) {
			return shopItem.getPriceInEuros() + " €";
		}
		return "$" + shopItem.getPriceInEuros();
	}

	public Set<User> findSellersOf(Article article) {
		Set<User> sellers = new TreeSet<User>();
		List<ShopItem> allItemsForSale = findAllItemsForSale();
		for (ShopItem shopItem : allItemsForSale) {
			if (shopItem.getArticle().getId().equals(article.getId())) {
				sellers.add(shopItem.getSeller());
			}
		}
		return sellers;
	}

	public Collection<ShopItem> findAllGamesForSale() {
		List<ShopItem> allItemsForSale = findAllItemsForSale();
		return new ArrayList<ShopItem>(filter(allItemsForSale, isGameShopItem));
	}

	public Collection<ShopItem> findAllHardwaresForSale() {
		List<ShopItem> allItemsForSale = findAllItemsForSale();
		return new ArrayList<ShopItem>(filter(allItemsForSale, isHardwareShopItem));
	}

	public long getNumHardwaresForSale() {
		return findAllHardwaresForSale().size();
	}

	public long getNumGamesForSale() {
		return findAllGamesForSale().size();
	}

}
