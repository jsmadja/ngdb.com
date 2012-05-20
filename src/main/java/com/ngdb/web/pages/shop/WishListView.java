package com.ngdb.web.pages.shop;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ngdb.entities.shop.Wish;

public class WishListView {

	@Property
	private Wish wish;

	@Property
	private List<Wish> wishes;

	@Inject
	private Session session;

	@SetupRender
	public void setupRender() {
		wishes = session.createCriteria(Wish.class).addOrder(Order.desc("modificationDate")).list();
	}
}
