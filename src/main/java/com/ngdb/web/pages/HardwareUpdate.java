package com.ngdb.web.pages;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.ngdb.entities.Hardware;
import com.ngdb.entities.Origin;
import com.ngdb.entities.Picture;
import com.ngdb.web.model.OriginList;

public class HardwareUpdate {

	@Property
	@Persist("entity")
	private Hardware hardware;

	@Property
	private String title;

	@Property
	private UploadedFile mainPicture;

	@Inject
	private Session session;

	@Property
	@Persist
	private String url;

	@Property
	private String details;

	@Property
	private Origin origin;

	@Property
	private Date releaseDate;

	void onActivate(Hardware hardware) {
		if (hardware != null) {
			this.hardware = hardware;
			this.details = hardware.getDetails();
			this.origin = hardware.getOrigin();
			this.releaseDate = hardware.getReleaseDate();
			this.details = hardware.getDetails();
			this.title = hardware.getTitle();
			this.url = hardware.getMainPicture().getUrl();
		}
	}

	@CommitAfter
	Object onSuccess() {
		Hardware hardware = new Hardware();
		hardware.setDetails(details);
		hardware.setOrigin(origin);
		hardware.setReleaseDate(releaseDate);
		hardware.setTitle(title);
		Picture picture = Picture.EMPTY;
		if (StringUtils.isNotBlank(url)) {
			picture = new Picture(url);
		}
		hardware.addPicture(picture);
		session.merge(hardware);
		return Games.class;
	}

	public SelectModel getOrigins() {
		return new OriginList(session.createCriteria(Origin.class).list());
	}

}
