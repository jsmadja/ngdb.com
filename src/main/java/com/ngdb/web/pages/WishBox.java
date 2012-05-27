package com.ngdb.web.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.shop.Wish;

public class WishBox {

	@Property
	private Wish wish;

	@Property
	private List<Wish> wishes;

	@Inject
	private com.ngdb.entities.WishBox wishBox;

	@SetupRender
	public void setupRender() {
		this.wishes = wishBox.findAllWishes();
	}
}
