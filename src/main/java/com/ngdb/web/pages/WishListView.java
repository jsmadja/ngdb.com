package com.ngdb.web.pages;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import com.ngdb.entities.Article;
import com.ngdb.entities.User;

public class WishListView {

	@Property
	private Article article;

	@Property
	private java.util.Collection<Article> articles;

	@Parameter(allowNull = true)
	private User user;

	@SetupRender
	public void setupRender() {
	}
}
