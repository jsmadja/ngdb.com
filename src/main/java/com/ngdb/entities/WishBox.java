package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.ngdb.Predicates.isGameWish;
import static com.ngdb.Predicates.isHardwareWish;
import static org.hibernate.criterion.Order.desc;
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

	public List<Wish> findAllWishes() {
		return session.createCriteria(Wish.class).addOrder(desc("modificationDate")).setCacheable(true).list();
	}

    public List<Wish> findAllGames() {
        return new ArrayList<Wish>(filter(findAllWishes(), isGameWish));
    }

    public List<Wish> findAllHardwares() {
        return new ArrayList<Wish>(filter(findAllWishes(), isHardwareWish));
    }

    public int getNumWishedHardwares() {
        return findAllHardwares().size();
    }

    public int getNumWishedGames() {
        return findAllGames().size();
    }
}
