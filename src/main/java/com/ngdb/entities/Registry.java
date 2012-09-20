package com.ngdb.entities;

import com.ngdb.entities.article.element.Note;
import com.ngdb.entities.article.element.Tag;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.hibernate.criterion.Order.asc;
import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;

public class Registry {

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private Session session;

    private static final Logger LOG = LoggerFactory.getLogger(Registry.class);

    public Collection<String> findAllTags() {
        return session.createCriteria(Tag.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

    public Collection<String> findAllPropertyNames() {
        return session.createCriteria(Note.class).setProjection(distinct(property("name"))).addOrder(asc("name")).list();
    }

}
