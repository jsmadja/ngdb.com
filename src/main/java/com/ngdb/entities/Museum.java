package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import com.ngdb.services.Cacher;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;

@SuppressWarnings("unchecked")
public class Museum {

	@Inject
	private Session session;

    @Inject
    private Cacher cacher;

	public int getRankOf(Article article) {
        if(cacher.hasRankOf(article)) {
            return cacher.getRankOf(article);
        }
        int rank = computeRankOf(article);
        cacher.setRankOf(article, rank);
        return rank;
	}

    private int computeRankOf(Article article) {
        List<Object[]> list = session.createSQLQuery("SELECT article_id,COUNT(*) FROM CollectionObject GROUP BY article_id ORDER BY COUNT(*) DESC").list();
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

    public void invalidateRanks() {
        cacher.invalidateRanks();
    }
}
