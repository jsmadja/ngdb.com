package com.ngdb.web.services.domain;

import static org.hibernate.criterion.Order.desc;
import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.ShopItem;
import com.ngdb.entities.user.User;
import com.ngdb.web.Category;

public class ShopService {

	@Inject
	private Session session;

	public List<ShopItem> findAll(Category category, Long id) {
		Criteria criteria = session.createCriteria(ShopItem.class).addOrder(desc("modificationDate"));
		switch (category) {
		case byArticle:
			Article article = (Article) session.load(Article.class, id);
			criteria = criteria.add(eq("article", article)).add(eq("sold", false));
			break;
		case byUser:
			User user = (User) session.load(User.class, id);
			criteria = criteria.add(eq("seller", user)).add(eq("sold", false));
			break;
		case bySoldDate:
			criteria = criteria.add(eq("sold", true));
			break;
		case none:
			criteria = criteria.add(eq("sold", false));
			break;
		}
		return criteria.list();
	}

}
