package com.ngdb.web.components.common.action;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import com.ngdb.entities.article.Article;

public class BuyButton {

	@Property
	@Parameter
	protected Article article;

	@Property
	@Parameter
	protected boolean showText;

}
