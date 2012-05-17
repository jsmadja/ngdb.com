package com.ngdb.web.pages;

import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.Origin;
import com.ngdb.web.model.OriginList;

public abstract class ArticleUpdate {

	@Property
	protected UploadedFile mainPicture;

	@Inject
	protected Session session;

	public SelectModel getOrigins() {
		return new OriginList(session.createCriteria(Origin.class).list());
	}

}
