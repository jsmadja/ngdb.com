package com.ngdb.entities;

import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.reference.Platform;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;

public class Registry {

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    public Collection<String> findAllTags() {
        return session.createCriteria(Tag.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

    public Collection<String> findAllPropertyNames() {
        return session.createCriteria(Note.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

    public Collection<Top100Item> findTop100OfGamesInCollectionByPlatform(Platform platform) {
        String sqlQuery = "SELECT g.id, g.title, g.origin_title, COUNT(co.article_id) FROM ngdb.CollectionObject co, ngdb.Game g WHERE g.id = co.article_id AND g.platform_short_name = '"+platform.getShortName()+"' GROUP BY co.article_id ORDER BY COUNT(co.article_id) DESC";
        Query query = session.createSQLQuery(sqlQuery);
        query = query.setResultTransformer(new ResultTransformer() {

            private Long oldCount = Long.MIN_VALUE;
            private Integer idx = 1;
            private String rank;

            @Override
            public Object transformTuple(Object[] tuple, String[] aliases) {
                long id = ((BigInteger) tuple[0]).longValue();
                String title = tuple[1].toString();
                String platform = tuple[2].toString();
                long count = ((BigInteger) tuple[3]).longValue();
                if(count == oldCount) {
                    rank = ".";
                } else {
                    rank = idx.toString()+".";
                }
                idx++;
                oldCount = count;
                return new Top100Item(id, title, platform, count, rank);
            }

            @Override
            public List transformList(List collection) {
                return collection;
            }
        });
        List list = query.setMaxResults(100).list();
        return list;
    }
}
