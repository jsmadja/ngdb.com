package com.ngdb.web.pages;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiresUser
public class Administration {

    @Property
    private Long articleId;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    @Inject
    private com.ngdb.entities.Market market;

    private static final Logger LOG = LoggerFactory.getLogger(Administration.class);

    @CommitAfter
    public Object onSuccess() {
        if(currentUser.isContributor()) {
            //session.delete(articleFactory.findById(articleId));
            //market.refresh();
            LOG.info(currentUser.getUsername()+" has just deleted "+articleId);
        }
        return this;
    }

}
