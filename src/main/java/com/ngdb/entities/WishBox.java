package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Projections.countDistinct;
import static org.hibernate.criterion.Restrictions.eq;

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

    private long findNum(String tableName) {
        return ((BigInteger)session.createSQLQuery("SELECT COUNT(w.article_id) FROM Wish w, "+tableName+" g WHERE g.id = w.article_id").uniqueResult()).longValue();
    }

    public long findNumGames() {
        return findNum("Game");
    }

    public long findNumAccessories() {
        return findNum("Accessory");
    }

    public long findNumHardwares() {
        return findNum("Hardware");
    }
}
