package com.ngdb.web.pages;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.CollectionObject;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.entities.WishBox;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.math.BigInteger;

public class Stats {

	@Property
	private Long wishListCount;

	@Property
	private Long userCount;

	@Property
	private Long soldCount;

    @Property
    private Article mostOwnedArticle;

	@Inject
	private com.ngdb.entities.Market market;

	@Inject
	private Population population;

	@Inject
	private WishBox wishBox;

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    @Property
    private Long mostOwnedArticleCount;

    @SetupRender
	public void init() {
		this.userCount = population.getNumUsers();
		this.wishListCount = wishBox.getNumWishes();
		this.soldCount = market.getNumSoldItems();

        Object[] result = (Object[]) session.createSQLQuery("SELECT article_id,COUNT(*) FROM CollectionObject GROUP BY article_id ORDER BY COUNT(*) DESC").setMaxResults(1).list().get(0);
        this.mostOwnedArticle =  articleFactory.findById(((BigInteger)result[0]).longValue());
	    this.mostOwnedArticleCount = ((BigInteger)result[1]).longValue();
    }

}
