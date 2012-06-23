package com.ngdb.web.pages.article.hardware;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;
import org.joda.time.DateTime;

import com.ngdb.entities.History;
import com.ngdb.entities.article.Hardware;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.PictureService;

@RequiresAuthentication
public class HardwareUpdate {

	@Property
	@Parameter(allowNull = true)
	private Hardware hardware;

	@Property
	@Validate("required,maxLength=255")
	private String title;

	@Property
	private UploadedFile mainPicture;

	@Inject
	private Session session;

	@Property
	private String details;

	@Property
	@Validate("required")
	private Origin origin;

	@Property
	@Validate("required")
	private Date releaseDate;

	@Property
	@Validate("required")
	private Platform platform;

	@InjectPage
	private HardwareView hardwareView;

	@Inject
	private PictureService pictureService;

	@Inject
	private ReferenceService referenceService;

	@Inject
	private History history;

	@Inject
	private CurrentUser currentUser;

	public void onActivate(Hardware hardware) {
		this.hardware = hardware;
	}

	@SetupRender
	public void setup() {
		if (hardware == null) {
			this.details = null;
			this.origin = null;
			this.releaseDate = new DateTime().withYear(1990).toDate();
			this.details = null;
			this.title = null;
			this.platform = null;
		} else {
			this.details = hardware.getDetails();
			this.origin = hardware.getOrigin();
			this.releaseDate = hardware.getReleaseDate();
			this.details = hardware.getDetails();
			this.title = hardware.getTitle();
			this.platform = hardware.getPlatform();
		}
	}

	@CommitAfter
	Object onSuccess() {
		Hardware hardware = new Hardware();
		if (isEditMode()) {
			hardware = this.hardware;
			hardware.updateModificationDate();
		}
		hardware.setDetails(details);
		hardware.setOrigin(origin);
		hardware.setReleaseDate(releaseDate);
		hardware.setTitle(title);
		hardware.setPlatform(platform);
		hardware = (Hardware) session.merge(hardware);
		if (this.mainPicture != null) {
			Picture picture = pictureService.store(mainPicture, hardware);
			hardware.addPicture(picture);
			session.merge(picture);
		}
		hardwareView.setHardware(hardware);
		history.add(hardware, currentUser.getUser());
		return hardwareView;
	}

	public boolean isEditMode() {
		return this.hardware != null;
	}

	public SelectModel getOrigins() {
		return new OriginList(referenceService.getOrigins());
	}

	public SelectModel getPlatforms() {
		return new PlatformList(referenceService.getPlatforms());
	}

}
