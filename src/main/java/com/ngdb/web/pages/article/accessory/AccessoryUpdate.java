package com.ngdb.web.pages.article.accessory;

import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.article.Accessory;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.hibernate.Session;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RequiresUser
public class AccessoryUpdate {

    @Persist
    @Property
    private Accessory accessory;

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
    private String ean;

    @Property
    private String reference;

    @Property
    private Origin origin;

    @Property
    @Validate("required")
    private Date releaseDate;

    @Property
    @Validate("required")
    private Platform platform;

    @InjectPage
    private AccessoryView accessoryView;

    @Inject
    private PictureService pictureService;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private CurrentUser currentUser;

    @Persist
    @Property
    private List<UploadedFile> pictures;

    @Property
    private Set<Picture> storedPictures;

    @Property
    private Picture picture;

    @Inject
    private ActionLogger actionLogger;

    public void onActivate(Accessory accessory) {
        this.accessory = accessory;
        if (pictures == null) {
            pictures = new ArrayList<UploadedFile>();
        }
    }

    @SetupRender
    public void setup() {
        if (accessory == null) {
            this.details = null;
            this.origin = null;
            this.releaseDate = new DateTime().withYear(1990).toDate();
            this.details = null;
            this.title = null;
            this.platform = null;
            this.ean = null;
            this.reference = null;
        } else {
            this.details = accessory.getDetails();
            this.origin = referenceService.findOriginByTitle(accessory.getOriginTitle());
            this.releaseDate = accessory.getReleaseDate();
            this.details = accessory.getDetails();
            this.title = accessory.getTitle();
            this.platform = referenceService.findPlatformByName(accessory.getPlatformShortName());
            this.ean = accessory.getUpc();
            this.reference = accessory.getReference();
            this.storedPictures = accessory.getPictures().all();
        }
    }

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {
        if (uploadedFile != null) {
            this.pictures.add(uploadedFile);
        }
    }

    @CommitAfter
    @DiscardAfter
    Object onSuccess() {
        Accessory accessory = new Accessory();
        if (isEditMode()) {
            accessory = this.accessory;
            accessory.updateModificationDate();
        }
        accessory.setDetails(details);
        accessory.setOrigin(origin);
        accessory.setReleaseDate(releaseDate);
        accessory.setTitle(title);
        accessory.setPlatform(platform);
        accessory.setUpc(ean);
        accessory.setReference(reference);
        accessory = (Accessory) session.merge(accessory);
        if (this.mainPicture != null) {
            Picture picture = pictureService.store(mainPicture, accessory);
            accessory.setCover(picture);
            if (isEditMode()) {
                session.merge(picture);
            }
        }
        if (pictures != null) {
            for (UploadedFile uploadedPicture : pictures) {
                Picture picture = pictureService.store(uploadedPicture, accessory);
                accessory.addPicture(picture);
                if (isEditMode()) {
                    session.merge(picture);
                }
            }
        }
        accessoryView.setAccessory(accessory);
        User user = currentUser.getUser();
        actionLogger.addEditAction(user, accessory);
        return accessoryView;
    }

    @CommitAfter
    Object onActionFromDeletePicture(Picture picture) {
        accessory.removePicture(picture);
        pictureService.delete(picture);
        this.storedPictures = accessory.getPictures().all();
        return this;
    }

    public String getSmallPictureUrl() {
        return picture.getUrl("small");
    }

    public boolean isEditMode() {
        return this.accessory != null;
    }

    public SelectModel getOrigins() {
        return new OriginList(referenceService.getOrigins());
    }

    public SelectModel getPlatforms() {
        return new PlatformList(referenceService.getPlatforms());
    }

}
