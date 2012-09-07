package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

import static org.hibernate.criterion.Projections.countDistinct;

public class WishBox {

	@Inject
	private Session session;

	public Long getNumWishes() {
		return (Long) session.createCriteria(Wish.class).setProjection(countDistinct("article")).setCacheable(true).setCacheRegion("cacheCount").uniqueResult();
	}

	public int getRankOf(Article article) {
		List<Object[]> list = session.createSQLQuery("SELECT article_id,COUNT(*) FROM Wish GROUP BY article_id ORDER BY COUNT(*) DESC").list();
		int rank = 1;
		for (Object[] o : list) {
			BigInteger articleId = (BigInteger) o[0];
			if (article.getId().equals(articleId.longValue())) {
				return rank;
			}
			rank++;
		}
		return Integer.MAX_VALUE;
	}

    public List<Wish> findAllGames() {
        return session.createSQLQuery("SELECT w.* FROM Wish w, Game g WHERE g.id = w.article_id").addEntity(Wish.class).setCacheable(true).list();
    }

    public List<Wish> findAllHardwares() {
        return session.createSQLQuery("SELECT w.* FROM Wish w, Hardware h WHERE h.id = w.article_id").addEntity(Wish.class).setCacheable(true).list();
    }

    public List<Wish> findAllAccessories() {
        return session.createSQLQuery("SELECT w.* FROM Wish w, Accessory a WHERE a.id = w.article_id").addEntity(Wish.class).setCacheable(true).list();
    }
}
