package com.ngdb.web.pages.article.game;

import com.ngdb.Barcoder;
import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Participation;
import com.ngdb.entities.Staff;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Picture;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.entities.user.User;
import com.ngdb.services.StaffParser;
import com.ngdb.web.model.OriginList;
import com.ngdb.web.model.PlatformList;
import com.ngdb.web.model.PublisherList;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.FileService;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.got5.tapestry5.jquery.JQueryEventConstants;
import org.hibernate.Session;
import org.joda.time.DateTime;

import java.util.*;

@RequiresUser
public class GameUpdate {

    @Property
    @Persist("entity")
    private Game game;

    @Property
    private UploadedFile mainPicture;

    @Inject
    private ReferenceService referenceService;

    @Inject
    private PictureService pictureService;

    @Inject
    private ArticleFactory articleFactory;

    @Property
    private String details;

    @Property
    private String staff;

    @Property
    private String ngh;

    @Property
    private String youtubePlaylist;

    @Property
    private String dailymotionPlaylist;

    @Property
    private String ean;

    @Property
    private String imdbId;

    @Property
    private String reference;

    @Property
    @Validate("required")
    private Origin origin;

    @Property
    @Validate("required")
    private Date releaseDate;

    @Property
    @Validate("required")
    private Publisher publisher;

    @Property
    @Validate("required")
    private Platform platform;

    @Property
    @Validate("required")
    private Long megaCount;

    @Property
    @Validate("required,maxLength=255")
    private String title;

    @InjectPage
    private GameView gameView;

    @Inject
    private Session session;

    @Inject
    private CurrentUser currentUser;

    @Persist
    @Property
    private List<UploadedFile> pictures;

    @Persist
    @Property
    private Set<Picture> storedPictures;

    @Property
    private Picture picture;

    @Inject
    private ActionLogger actionLogger;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Inject
    private FileService fileService;

    @Inject
    private Barcoder barcoder;

    public void onActivate(Game game) {
        this.game = game;
        if (pictures == null) {
            pictures = new ArrayList<UploadedFile>();
        }
    }

    Game onPassivate() {
        return game;
    }

    @SetupRender
    public void setup() {
        if (game == null) {
            this.publisher = null;
            this.platform = null;
            this.megaCount = null;
            this.details = null;
            this.origin = null;
            this.releaseDate = new DateTime().withYear(1990).toDate();
            this.details = null;
            this.title = null;
            this.ngh = null;
            this.ean = null;
            this.imdbId = null;
            this.reference = null;
            this.youtubePlaylist = null;
            this.dailymotionPlaylist = null;
            this.staff = null;
        } else {
            this.publisher = game.getPublisher();
            this.platform = referenceService.findPlatformByName(game.getPlatformShortName());
            this.megaCount = game.getMegaCount();
            this.details = game.getDetails();
            this.origin = referenceService.findOriginByTitle(game.getOriginTitle());
            this.releaseDate = game.getReleaseDate();
            this.details = game.getDetails();
            this.title = game.getTitle();
            this.ngh = game.getNgh();
            this.ean = game.getUpc();
            this.imdbId = game.getImdbId();
            this.reference = game.getReference();
            this.storedPictures = game.getPictures().all();
            this.youtubePlaylist = articleFactory.findYoutubePlaylistOf(game);
            this.dailymotionPlaylist = articleFactory.findDailymotionPlaylistOf(game);
        }
    }

    @OnEvent(component = "uploadImage", value = JQueryEventConstants.AJAX_UPLOAD)
    void onImageUpload(UploadedFile uploadedFile) {
        this.pictures.add(uploadedFile);
    }

    @CommitAfter
    @DiscardAfter
    public Object onSuccess() {
        Game game = new Game();
        if (isEditMode()) {
            game = this.game;
            game.updateModificationDate();
            barcoder.invalidate(this.ean);
        }
        game.setDetails(details);
        game.setOrigin(origin);
        game.setReleaseDate(releaseDate);
        game.setTitle(title);
        game.setPublisher(publisher);
        game.setPlatform(platform);
        game.setMegaCount(megaCount);
        game.setNgh(ngh);
        game.setUpc(ean);
        game.setImdbId(imdbId);
        game.setReference(reference);
        game.setYoutubePlaylist(youtubePlaylist);
        game.setDailymotionPlaylist(dailymotionPlaylist);

        if (staff != null) {
            List<Participation> participations = new StaffParser().createFrom(staff, game);
            for (Participation participation : participations) {
                session.merge(participation);
            }
        }

        if (this.mainPicture != null) {
            Picture picture = pictureService.store(mainPicture, game);
            game.setCover(picture);
            if (isEditMode()) {
                session.merge(picture);
            }
        }
        if (pictures != null) {
            for (UploadedFile uploadedPicture : pictures) {
                Picture picture = pictureService.store(uploadedPicture, game);
                game.addPicture(picture);
                if (isEditMode()) {
                    session.merge(picture);
                }
            }
        }
        game = (Game) session.merge(game);
        gameView.setGame(game);
        User user = currentUser.getUser();
        actionLogger.addEditAction(user, game);
        return gameView;
    }

    @CommitAfter
    Object onActionFromDeletePicture(Picture picture) {
        game.removePicture(picture);
        pictureService.delete(picture);
        pictureService.invalidateCoverOf(game);
        this.storedPictures = game.getPictures().all();
        return this;
    }

    public String getSmallPictureUrl() {
        return picture.getUrl("small");
    }

    public boolean isEditMode() {
        return this.game != null;
    }

    public SelectModel getPlatforms() {
        return new PlatformList(referenceService.getPlatforms());
    }

    public SelectModel getPublishers() {
        return new PublisherList(referenceService.getPublishers());
    }

    public SelectModel getOrigins() {
        return new OriginList(referenceService.getOrigins());
    }

}
