package com.ngdb.entities.article.element;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

@Embeddable
public class Pictures {

	@ElementCollection
	@JoinTable(name = "ArticlePictures", joinColumns = { @JoinColumn(name = "article_id") })
	private Set<Picture> pictures;

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
