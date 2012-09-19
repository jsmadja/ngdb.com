package com.ngdb.entities;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.ForumCode;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.hibernate.Criteria;
import org.hibernate.Session;

import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static org.hibernate.criterion.Projections.count;
import static org.hibernate.criterion.Restrictions.eq;

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
        List sold = allShopItems().add(eq("sold", false)).list();
        return sold;
    }

    private Criteria allShopItems() {
        return session.createCriteria(ShopItem.class);
    }

    public List<ShopItem> findRandomForSaleItems(int count) {
        List<ShopItem> forSaleItems = new ArrayList<ShopItem>(getShopItemsWithCover());
        List<ShopItem> randomItems = new ArrayList<ShopItem>();
        Set<Integer> ids = new HashSet<Integer>();

        if(currentUser.isLogged()) {
            User user = currentUser.getUserFromDb();
            forSaleItems.removeAll(user.getBasket().all());
            forSaleItems.removeAll(user.getShop().all());
        }

        if(count > forSaleItems.size()) {
            count = forSaleItems.size();
        }

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

    private Collection<ShopItem> getShopItemsWithCover() {
        if (getCache() == null) {
            List<ShopItem> items = session.createQuery("SELECT si FROM ShopItem si WHERE si.sold = false").list();
            items = new ArrayList<ShopItem>(filter(items, new Predicate<ShopItem>() {
                @Override
                public boolean apply(@Nullable ShopItem input) {
                    return input.hasCover();
                }
            }));
            cache.put(new Element("all", Collections.unmodifiableCollection(items)));
        }
        return (Collection<ShopItem>) getCache().getValue();
    }

    private Element getCache() {
        return cache.get("all");
    }

    public Long getNumForSaleItems() {
        return (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", false)).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public Long getNumSoldItems() {
        return (Long) session.createCriteria(ShopItem.class).setProjection(count("id")).add(eq("sold", true)).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public void potentialBuyer(ShopItem shopItem, User potentialBuyer) {
        shopItem.addPotentialBuyer(potentialBuyer);
    }

    public void removeFromBasket(User potentialBuyer, ShopItem shopItem) {
        shopItem.removePotentialBuyer(potentialBuyer);
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
        return findAll("Game");
    }

    private List findAll(String tableName) {
        return session.createSQLQuery("SELECT * FROM ShopItem WHERE sold = 0 AND article_id IN (SELECT id FROM "+tableName+")").addEntity(ShopItem.class).list();
    }

    public List<ShopItem> findAllAccessoriesForSale() {
        return findAll("Accessory");
    }

    public List<ShopItem> findAllHardwaresForSale() {
        return findAll("Hardware");
    }

    public long getNumGamesForSale() {
        return getNumAll("Game");
    }

    public long getNumHardwaresForSale() {
        return getNumAll("Hardware");
    }

    public long getNumAccessoriesForSale() {
        return getNumAll("Accessory");
    }

    public long getNumHardwaresForSaleBy(User user) {
        return getNum(user, "Hardware");
    }

    public long getNumAccessoriesForSaleBy(User user) {
        return getNum(user, "Accessory");
    }

    public long getNumGamesForSaleBy(User user) {
        return getNum(user, "Game");
    }

    public List<ShopItem> getAllHardwaresForSaleBy(User user) {
        return getAll(user, "Hardware");
    }

    public List<ShopItem> getAllGamesForSaleBy(User user) {
        return getAll(user, "Game");
    }

    public List<ShopItem> getAllAccessoriesForSaleBy(User user) {
        return getAll(user, "Accessory");
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

    public void sell(ShopItem shopItem) {
        shopItem.sold();
        session.merge(shopItem);
        cache.flush();
    }

    private long getNumAll(String tableName) {
        return ((BigInteger) session.createSQLQuery("SELECT COUNT(id) FROM ShopItem WHERE sold = 0 AND article_id IN (SELECT id FROM "+tableName+")").uniqueResult()).longValue();
    }

    private long getNum(User user, String tableName) {
        return ((BigInteger) session.createSQLQuery("SELECT COUNT(id) FROM ShopItem WHERE sold = 0 AND seller_id = "+user.getId()+" AND article_id IN (SELECT id FROM "+tableName+")").uniqueResult()).longValue();
    }

    private List getAll(User user, String tableName) {
        return session.createSQLQuery("SELECT * FROM ShopItem WHERE sold = 0 AND seller_id = "+user.getId()+" AND article_id IN (SELECT id FROM "+tableName+")").addEntity(ShopItem.class).list();
    }

    public void refresh() {
        cache.remove("all");
    }
}
