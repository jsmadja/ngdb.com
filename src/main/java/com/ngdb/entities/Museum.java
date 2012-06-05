package com.ngdb.entities;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.ngdb.Functions.fromCollectionObjectToArticle;
import static com.ngdb.Predicates.isGame;
import static com.ngdb.Predicates.isHardware;
import static org.hibernate.criterion.Restrictions.eq;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;

@SuppressWarnings("unchecked")
public class Museum {

	@Inject
	private Session session;

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

	public void add(User user, Article article) {
		CollectionObject collectionObject = new CollectionObject(user, article);
		user.addInCollection(collectionObject);
		session.merge(collectionObject);
	}

	public Collection<Article> findGamesOf(User user) {
		return filter(findAllArticlesInMuseum(user), isGame);
	}

	public Collection<Article> findHardwaresOf(User user) {
		return filter(findAllArticlesInMuseum(user), isHardware);
	}

	private Collection<Article> findAllArticlesInMuseum(User user) {
		return transform(session.createCriteria(CollectionObject.class).add(eq("owner", user)).list(), fromCollectionObjectToArticle);
	}

}
