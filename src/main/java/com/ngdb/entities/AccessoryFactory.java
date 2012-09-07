package com.ngdb.entities;

import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import java.util.List;

import static org.hibernate.criterion.Projections.rowCount;

@SuppressWarnings("unchecked")
public class AccessoryFactory {

    @Inject
    private Session session;

    public Long getNumAccessories() {
        return (Long) session.createCriteria(Accessory.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public List<Accessory> findAll() {
        return allAccessories().list();
    }

    private Criteria allAccessories() {
        return session.createCriteria(Accessory.class).setCacheable(true);
    }

    public List<Accessory> findAllOwnedBy(final User user) {
        return session.createSQLQuery("SELECT a.* FROM CollectionObject co, Accessory a WHERE user_id = "+user.getId()+" AND co.article_id = a.id").addEntity(Hardware.class).setCacheable(true).list();
    }

}
