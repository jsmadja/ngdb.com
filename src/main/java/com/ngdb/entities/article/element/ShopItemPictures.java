package com.ngdb.entities.article.element;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class ShopItemPictures {

	@OneToMany(mappedBy = "shopItem")
	private Set<Picture> pictures = new HashSet<Picture>();

	public Picture first() {
		if (pictures == null || pictures.isEmpty()) {
			return Picture.EMPTY;
		}
		return pictures.iterator().next();
	}

	public void add(Picture picture) {
		pictures.add(picture);
	}

}
