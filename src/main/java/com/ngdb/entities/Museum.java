package com.ngdb.entities;

import static org.hibernate.criterion.Restrictions.eq;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;

@SuppressWarnings("unchecked")
public class Museum {

	@Inject
	private Session session;

	private List<CollectionObject> findCollectionOf(User loggedUser) {
		List<CollectionObject> collectionObjects = session.createCriteria(CollectionObject.class).add(eq("owner", loggedUser)).list();
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

	public Collection<CollectionObject> findGamesOf(User user) {
		List<CollectionObject> collectionObjects = findCollectionOf(user);
		return Collections2.filter(collectionObjects, new Predicate<CollectionObject>() {
			@Override
			public boolean apply(CollectionObject input) {
				return input.getArticle() instanceof Game;
			}
		});
	}

	public Collection<CollectionObject> findHardwaresOf(User user) {
		List<CollectionObject> collectionObjects = findCollectionOf(user);
		return Collections2.filter(collectionObjects, new Predicate<CollectionObject>() {
			@Override
			public boolean apply(CollectionObject input) {
				return input.getArticle() instanceof Hardware;
			}
		});
	}

	public void add(User user, Article article) {
		CollectionObject collectionObject = new CollectionObject(user, article);
		user.addInCollection(collectionObject);
		session.merge(collectionObject);
	}

}
