package com.ngdb.web.pages;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ngdb.entities.Hardware;
import com.ngdb.entities.Origin;
import com.ngdb.entities.Picture;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.services.NotCreatedException;
import com.ngdb.web.services.PictureService;

public class HardwareUpdate {

	@Property
	@Persist("entity")
	private Hardware hardware;

	@Property
	@Validate("required,maxLength=255")
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
	@Validate("required")
	private Origin origin;

	@Property
	@Validate("required")
	private Date releaseDate;

	@InjectPage
	private HardwareView hardwareView;

	@Inject
	protected PictureService pictureService;

	private Logger log = LoggerFactory.getLogger(HardwareUpdate.class);

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
		if (isEditMode()) {
			hardware = this.hardware;
		}
		hardware.setDetails(details);
		hardware.setOrigin(origin);
		hardware.setReleaseDate(releaseDate);
		hardware.setTitle(title);
		Picture picture = Picture.EMPTY;
		if (StringUtils.isNotBlank(url)) {
			try {
				picture = pictureService.store(url, hardware);
			} catch (NotCreatedException e) {
				log.error("Cannot create image for hardware:" + hardware.getId() + " with url':" + url + "'");
			}
		}
		hardware.addPicture(picture);
		session.merge(hardware);
		hardwareView.setHardware(hardware);
		return hardwareView;
	}

	public boolean isEditMode() {
		return this.hardware != null;
	}

	public SelectModel getOrigins() {
		return new OriginList(session.createCriteria(Origin.class).list());
	}

}
