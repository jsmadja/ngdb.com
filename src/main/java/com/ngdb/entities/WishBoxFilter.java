package com.ngdb.entities;

import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.Wish;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.countDistinct;
import static org.hibernate.criterion.Projections.id;
import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.in;

public class WishBoxFilter extends AbstractFilter {

    private WishBox wishBox;
    private Session session;

    public WishBoxFilter(WishBox wishBox, Session session) {
        this.wishBox = wishBox;
        this.session = session;
        clear();
    }

    public List<Wish> getWishes() {
        if(isFilteredByAnUserWithoutWishes()) {
            return new ArrayList<Wish>();
        }
        Criteria articleCriteria = createArticleCriteria();
        articleCriteria = addPlatformCriteria(articleCriteria, filteredPlatform);
        articleCriteria = addOriginCriteria(articleCriteria, filteredOrigin);
        List<Long> articles = articleCriteria.list();
        if(articles.isEmpty()) {
            return new ArrayList<Wish>();
        }
        Criteria criteria = session.createCriteria(Wish.class);
        criteria = addUserFilter(criteria);
        return criteria.add(in("article.id", articles)).addOrder(desc("modificationDate")).list();
    }

    private Criteria addUserFilter(Criteria criteria) {
        if(filteredUser != null) {
            criteria = criteria.add(eq("wisher", filteredUser));
        }
        return criteria;
    }

    private Criteria createArticleCriteria() {
        Class<? extends Article> clazz = Accessory.class;
        if(isFilteredByGames()) {
            clazz = Game.class;
        }
        if(isFilteredByHardwares()) {
            clazz = Hardware.class;
        }
        return session.createCriteria(clazz).setProjection(id());
    }

    private Criteria addPlatformCriteria(Criteria criteria, Platform platform) {
        if(platform == null) {
            return criteria;
        }
        return criteria.add(eq("platformShortName", platform.getShortName()));
    }

    private Criteria addOriginCriteria(Criteria criteria, Origin origin) {
        if(origin == null) {
            return criteria;
        }
        return criteria.add(eq("originTitle", origin.getTitle()));
    }

    private boolean isFilteredByAnUserWithoutWishes() {
        return filteredUser != null && filteredUser.getNumArticlesInWishList() == 0;
    }

    public int getNumWishesInThisOrigin(Origin origin) {
        if(isFilteredByAnUserWithoutWishes()) {
            return 0;
        }
        Criteria articleCriteria = createArticleCriteria();
        if (filteredPlatform != null) {
            articleCriteria = addPlatformCriteria(articleCriteria, filteredPlatform);
        }
        articleCriteria = addOriginCriteria(articleCriteria, origin);
        List articles = articleCriteria.list();
        if(articles.isEmpty()) {
            return 0;
        }
        Criteria criteria = createWishCriteria().setProjection(countDistinct("id"));
        criteria = addUserFilter(criteria);
        return ((Long)criteria.add(in("article.id", articles)).uniqueResult()).intValue();
    }

    private Criteria createWishCriteria() {
        return session.createCriteria(Wish.class);
    }

    public int getNumWishesInThisPlatform(Platform platform) {
        if(isFilteredByAnUserWithoutWishes()) {
            return 0;
        }
        Criteria articleCriteria = createArticleCriteria();
        articleCriteria = addPlatformCriteria(articleCriteria, platform);
        List articlesInThisPlatform = articleCriteria.list();
        if(articlesInThisPlatform.isEmpty()) {
            return 0;
        }
        Criteria criteria = createWishCriteria().setProjection(countDistinct("id"));
        criteria = addUserFilter(criteria);
        criteria = criteria.add(in("article.id", articlesInThisPlatform));
        return ((Long)criteria.uniqueResult()).intValue();
    }

    public long getNumHardwares() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedHardwares();
        }
        return wishBox.findNumHardwares();
    }

    public long getNumGames() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedGames();
        }
        return wishBox.findNumGames();
    }

    @Override
    public long getNumAccessories() {
        if (filteredUser != null) {
            return filteredUser.getNumWishedAccessories();
        }
        return wishBox.findNumAccessories();
    }

}
