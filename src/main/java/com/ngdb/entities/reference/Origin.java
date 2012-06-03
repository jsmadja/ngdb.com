package com.ngdb.entities.reference;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ngdb.entities.AbstractEntity;

@Entity
@XmlRootElement(name = "origin")
@XmlAccessorType(FIELD)
public class Origin extends AbstractEntity implements Comparable<Origin> {

	@Column(unique = true, nullable = false)
	private String title;

	public Origin() {
	}

	public Origin(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int compareTo(Origin origin) {
		return title.compareTo(origin.title);
	}

}
