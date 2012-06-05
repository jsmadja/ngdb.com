package com.ngdb.web.pages;

import static org.apache.commons.lang.StringUtils.isNotBlank;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.Population;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
import com.ngdb.web.Category;

public class WishBox {

	@Property
	private Wish wish;

	@Property
	private Collection<Wish> wishes;

	@Persist
	private Category category;

	@Inject
	private com.ngdb.entities.WishBox wishBox;

	@Inject
	private Population population;

	private Long id;

	void onActivate(String category, String value) {
		if (isNotBlank(category)) {
			this.category = Category.valueOf(Category.class, category);
			if (StringUtils.isNumeric(value)) {
				id = Long.valueOf(value);
			}
		}
	}

	@SetupRender
	public void setupRender() {
		if (category == null || category == Category.none) {
			this.wishes = wishBox.findAllWishes();
		} else {
			switch (category) {
			case byUser:
				User user = population.findById(id);
				this.wishes = user.getWishes();
				break;
			}
		}
	}
}
