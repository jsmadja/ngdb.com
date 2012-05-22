package com.ngdb.web.services.domain;

import static org.hibernate.criterion.Restrictions.eq;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;

public class CollectionService {

	@Inject
	private Session session;

	public List findCollectionOf(User loggedUser) {
		List collectionObjects = session.createCriteria(CollectionObject.class).add(eq("owner", loggedUser)).list();
		Collections.sort(collectionObjects);
		return collectionObjects;
	}

	public String getRankOf(Article article) {
		List<Object[]> list = session.createSQLQuery("SELECT article_id,COUNT(*) FROM CollectionObject GROUP BY article_id ORDER BY COUNT(*) DESC").list();
		int rank = 1;
		for (Object[] o : list) {
			BigInteger articleId = (BigInteger) o[0];
			if (article.getId().equals(articleId.longValue())) {
				return Integer.toString(rank);
			}
		}
		return "N/A";
	}

}
