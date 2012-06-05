package com.ngdb.entities.article;

import javax.persistence.Entity;

@Entity
public class Hardware extends Article {

	@Override
	public Class<?> getType() {
		return Hardware.class;
	}

}
