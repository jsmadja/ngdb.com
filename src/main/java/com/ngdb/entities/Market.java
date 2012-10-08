package com.ngdb.entities;

import com.google.common.base.Predicate;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.services.MailService;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.UnavailableRatingException;
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
        return allShopItems().add(eq("sold", false)).list();
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

    public String getPriceForCurrentUser(ShopItem shopItem) {
        if(currentUser.isAnonymous()) {
            return shopItem.getPriceInCustomCurrency() + " "+shopItem.getCustomCurrency();
        }
        String preferredCurrency = currentUser.getPreferedCurrency();
        try {
            return shopItem.getPriceIn(preferredCurrency) + " "+ preferredCurrency;
        }catch(UnavailableRatingException e) {
            return shopItem.getPriceInCustomCurrency() + " "+shopItem.getCustomCurrency();
        }
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

    public void refresh() {
        cache.remove("all");
    }

    public void tellWishers(final ShopItem shopItem) {
        Collection<User> wishers = shopItem.getWishers();
        for (User wisher:wishers) {
            sendEmailToWisher(shopItem, wisher);
        }
    }

    private void sendEmailToWisher(final ShopItem shopItem, final User wisher) {
        Map<String, String> params = new HashMap<String, String>() {{
            Article article = shopItem.getArticle();
            User user = shopItem.getSeller();
            String origin = article.getOriginTitle();
            String platform = article.getPlatformShortName();
            put("username", wisher.getLogin());
            put("seller", user.getLogin());
            put("articleTitle", shopItem.getTitle());
            put("articleOrigin", origin);
            put("articlePlatform", platform);
            put("description", shopItem.getDetails());
            put("price", shopItem.getPriceAsStringIn(wisher.getPreferedCurrency()));
            put("state", shopItem.getState().getTitle());
            put("url", hostUrl + "market/"+user.getId()+"?platform=" + platform + "&origin=" + origin);
        }};
        mailService.sendMail(wisher, "new_shopitem", "Your dream comes true, you could buy "+shopItem.getTitle(), params);
    }
}
