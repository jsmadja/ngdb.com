package com.ngdb.entities.article.element;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShopItemPictures implements Iterable<Picture> {

	@OneToMany(mappedBy = "shopItem", fetch = FetchType.LAZY)
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

	@Override
	public Iterator<Picture> iterator() {
		return pictures.iterator();
	}

}
