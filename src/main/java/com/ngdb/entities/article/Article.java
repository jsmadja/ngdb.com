package com.ngdb.entities.article;

import com.google.common.base.Objects;
import com.ngdb.entities.Participation;
import com.ngdb.entities.Staff;
import com.ngdb.entities.article.element.*;
import com.ngdb.entities.reference.Origin;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;
import com.ngdb.entities.user.CollectionObject;
import com.ngdb.entities.user.User;
import com.ngdb.services.SentenceTokenizerFactory;
import com.ngdb.services.ToStringBridge;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AnalyzerDefs({
        @AnalyzerDef(name = "noaccent",
                tokenizer = @TokenizerDef(factory = SentenceTokenizerFactory.class),
                filters = {
                        @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                        @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
                })
})
public abstract class Article implements Comparable<Article>, Serializable {

    private static final int MAX_DETAIL_LENGTH = 1024;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "modification_date", nullable = false)
    private Date modificationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(nullable = false)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "origin_title")
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String originTitle;

    @Column(name = "platform_short_name")
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String platformShortName;

    @OneToOne(fetch = FetchType.LAZY)
    @FieldBridge(impl = ToStringBridge.class)
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private Publisher publisher;

    @Embedded
    @IndexedEmbedded
    private Notes notes;

    @Embedded
    @IndexedEmbedded
    private Tags tags;

    @Embedded
    @IndexedEmbedded
    private Files files;

    @Embedded
    private ArticlePictures pictures;

    @Column(name = "cover_url", nullable = true)
    protected String coverUrl;

    @Column(name = "youtube_playlist", nullable = true)
    private String youtubePlaylist;

    @Column(name = "dailymotion_playlist", nullable = true)
    private String dailymotionPlaylist;

    @Embedded
    @IndexedEmbedded
    private Reviews reviews;

    @Embedded
    @IndexedEmbedded
    private Comments comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<CollectionObject> owners;

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String details;

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String upc;

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String reference;

    @Embedded
    private Staff staff;

    public void addParticipation(Participation participation) {
        staff.add(participation);
    }

    Article() {
        this.creationDate = this.modificationDate = new Date();
    }

    public void updateModificationDate() {
        modificationDate = new Date();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Article) {
            Article a = (Article) obj;
            if (id.equals(a.id)) {
                return true;
            }
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title);
    }

    public void setOrigin(Origin origin) {
        this.originTitle = origin.getTitle();
    }

    public void addPicture(Picture picture) {
        pictures.add(picture);
    }

    public ArticlePictures getPictures() {
        return pictures;
    }

    public Notes getNotes() {
        return notes;
    }

    public Tags getTags() {
        return tags;
    }

    public Files getFiles() {
        return files;
    }

    // utilise dans ArticleStats
    public Set<User> getOwners() {
        Set<User> users = new HashSet<User>();
        for (CollectionObject owner : owners) {
            users.add(owner.getOwner());
        }
        return users;
    }

    public void setCover(Picture picture) {
        this.coverUrl = picture.getUrl();
    }

    public void setCover(String url) {
        this.coverUrl = url;
    }

    public Picture getCover() {
        if (coverUrl == null) {
            if (isGame()) {
                return Picture.emptyOf(platformShortName);
            }
            return Picture.emptyOfHardware();
        }
        return new Picture(coverUrl);
    }

    public void setDetails(String details) {
        details = StringUtils.defaultString(details);
        int end = details.length() < MAX_DETAIL_LENGTH ? details.length() : MAX_DETAIL_LENGTH;
        this.details = details.substring(0, end);
    }

    public String getDetails() {
        return details;
    }

    public int getOwnersCount() {
        return owners.size();
    }

    public Reviews getReviews() {
        return reviews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Comments getComments() {
        return comments;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(Article article) {
        return title.compareToIgnoreCase(StringUtils.defaultString(article.title));
    }

    @Override
    public String toString() {
        return title;
    }

    public void setPlatform(Platform platform) {
        this.platformShortName = platform.getShortName();
    }

    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new Reviews();
        }
        reviews.add(review);
    }

    public void removePicture(Picture picture) {
        pictures.remove(picture);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public boolean hasCover() {
        return getCover().isNotEmpty();
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUpc() {
        return upc;
    }

    public String getSuffix() {
        String[] toReplace = {"·", ",", "&", "/", " ", "!", "\\(", "\\)", "'", "~"};
        String title = getTitle();
        for (String s : toReplace) {
            if (title == null) {
                title = "";
            }
            title = title.replaceAll(s, "-");
        }
        title += "-" + getPlatformShortName() + "-" + getOriginTitle();
        while (title.contains("--")) {
            title = title.replaceAll("--", "-");
        }
        return title;
    }

    public abstract boolean isGame();

    public abstract boolean isHardware();

    public abstract boolean isAccessory();

    public abstract String getViewPage();

    public abstract String getUpdatePage();

    public String getAverageMark() {
        if (!getHasReviews()) {
            return "0";
        }
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getMarkInPercent();
        }
        return (sum / reviews.count()) + "%";
    }

    public boolean getHasReviews() {
        if (reviews == null) {
            return false;
        }
        return reviews.count() > 0;
    }

    public void addFile(File file) {
        files.add(file);
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public String getPlatformShortName() {
        return platformShortName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public void setPlatformShortName(String platformShortName) {
        this.platformShortName = platformShortName;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public String getYoutubePlaylist() {
        return youtubePlaylist;
    }

    public void setYoutubePlaylist(String youtubePlaylist) {
        this.youtubePlaylist = youtubePlaylist;
    }

    public String getDailymotionPlaylist() {
        return dailymotionPlaylist;
    }

    public void setDailymotionPlaylist(String dailymotionPlaylist) {
        this.dailymotionPlaylist = dailymotionPlaylist;
    }

    public Staff getStaff() {
        return staff;
    }

    public boolean hasStaff() {
        return staff != null && !staff.isEmpty();
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

}
