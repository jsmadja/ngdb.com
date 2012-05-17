package com.ngdb.web.pages;

import java.util.Collection;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import com.ngdb.entities.Article;
import com.ngdb.entities.Game;
import com.ngdb.entities.User;

public class CollectionView {

	@Parameter(allowNull = true)
	private User user;

	@Property
	private Game game;

	@Property
	private Collection<Article> games;

	@SetupRender
	public void setupRender() {
	}

}
