package com.ngdb.entities.article;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hardware extends Article {

	@Override
	public Class<?> getType() {
		return Hardware.class;
	}

}
