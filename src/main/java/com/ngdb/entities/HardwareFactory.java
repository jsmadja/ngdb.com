package com.ngdb.entities;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.rowCount;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.User;

@SuppressWarnings("unchecked")
public class HardwareFactory {

    @Inject
    private Session session;

    public Long getNumHardwares() {
        return (Long) session.createCriteria(Hardware.class).setProjection(rowCount()).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
    }

    public List<Hardware> findAll() {
        return allHardwares().list();
    }

    private Criteria allHardwares() {
        return session.createCriteria(Hardware.class).setCacheable(true).addOrder(asc("title"));
    }

    public List<Article> findAllOwnedBy(final User owner) {
        return new ArrayList<Article>(Collections2.filter(findAll(), new Predicate<Article>() {
            @Override
            public boolean apply(Article input) {
                return owner.owns(input);
            }
        }));
    }

}
