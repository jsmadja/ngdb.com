package com.ngdb.entities.article.element;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Embeddable
@XmlAccessorType(FIELD)
@XmlRootElement(name = "pictures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ArticlePictures implements Iterable<Picture> {

	@ElementCollection
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	@XmlElements({ @XmlElement(name = "picture") })
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
