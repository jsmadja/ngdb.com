package com.ngdb.entities.article.element;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@Embeddable
@XmlAccessorType(FIELD)
@XmlRootElement(name = "pictures")
public class Pictures {

	@ElementCollection
	@XmlElements({ @XmlElement(name = "picture") })
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
