package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.ShopItemPredicates.isGameShopItem;
import static com.ngdb.ShopItemPredicates.isHardwareShopItem;
import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Nullable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.ForumCode;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.hibernate.criterion.Projections;

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

    private static Cache cache;

    static {
        CacheManager create = CacheManager.create();
        cache = create.getCache("index.random.shopitem");
    }

    public List<ShopItem> findAllItemsForSale() {
        return allShopItems().add(eq("sold", false)).list();
    }

    private Criteria allShopItems() {
        return session.createCriteria(ShopItem.class).setCacheable(true);
    }

    public List<ShopItem> findRandomForSaleItems(int count) {
        List<ShopItem> forSaleItems = getShopItemsWithCover();
        List<ShopItem> randomItems = new ArrayList<ShopItem>();
        Set<Integer> ids = new HashSet<Integer>();
        while (ids.size() < count) {
            int randomIdx = RandomUtils.nextInt(forSaleItems.size());
            if (ids.add(randomIdx)) {
                ShopItem shopItem = forSaleItems.get(randomIdx);
                shopItem = (ShopItem) session.load(ShopItem.class, shopItem.getId());
                randomItems.add(shopItem);
            }
        }
        return randomItems;
    }

    private List<ShopItem> getShopItemsWithCover() {
        if (cache.get("all") == null) {
            List<ShopItem> items = session.createQuery("SELECT si FROM ShopItem si WHERE si.sold = false").list();
            items = new ArrayList<ShopItem>(Collections2.filter(items, new Predicate<ShopItem>() {
                @Override
                public boolean apply(@Nullable ShopItem input) {
                    return input.hasCover();
                }
            }));
            cache.put(new Element("all", items));
            return items;
        }
        return (List<ShopItem>) cache.get("all").getValue();
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

    public String getPriceOf(ShopItem shopItem) {
        String preferedCurrency = currentUser.getPreferedCurrency();
        if ("USD".equalsIgnoreCase(preferedCurrency)) {
            return "$" + shopItem.getPriceInDollars();
        }
        if ("EUR".equalsIgnoreCase(preferedCurrency)) {
            return shopItem.getPriceInEuros() + " €";
        }
        if (shopItem.getPriceIn(preferedCurrency) != null) {
            return shopItem.getPriceIn(preferedCurrency) + " " + preferedCurrency;
        }
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

    public List<ShopItem> findAllGamesForSale() {
        return session.createSQLQuery("SELECT * FROM ShopItem WHERE article_id IN (SELECT id FROM Game)").addEntity(ShopItem.class).list();
    }

    public long getNumGamesForSale() {
        return ((BigInteger) session.createSQLQuery("SELECT COUNT(id) FROM ShopItem WHERE article_id IN (SELECT id FROM Game)").uniqueResult()).longValue();
    }

    public List<ShopItem> findAllHardwaresForSale() {
        return session.createSQLQuery("SELECT * FROM ShopItem WHERE article_id IN (SELECT id FROM Hardware)").addEntity(ShopItem.class).list();
    }

    public long getNumHardwaresForSale() {
        return ((BigInteger) session.createSQLQuery("SELECT COUNT(id) FROM ShopItem WHERE article_id IN (SELECT id FROM Hardware)").uniqueResult()).longValue();
    }

    public String asVBulletinCode() {
        List<ShopItem> shopItems = findAllItemsForSale();
        return ForumCode.asVBulletinCode(shopItems);
    }

    public List<ShopItem> getShopItemsForSaleOf(User user) {
        return session.createCriteria(ShopItem.class).
            add(eq("seller", user)).
            add(eq("sold", false)).
            setCacheable(true).list();
    }
}
