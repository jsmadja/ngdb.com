package com.ngdb.entities.article.element;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag extends AbstractEntity {

	@Column(nullable = false, unique = true)
	private String name;

	Tag() {
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
