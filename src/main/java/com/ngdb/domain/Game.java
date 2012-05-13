package com.ngdb.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game implements BaseEntity {

	public static final Game EMPTY = new Game();

	private static long ID = 0;

	private Long id;

	private String ngh = "";

	private Publisher publisher;

	private String title = "";

	private Long megaCount = 0L;

	private String genre = "";

	private String releaseDate;

	private Origin origin;

	private Platform platform;

	private List<Property> properties = new ArrayList<Property>();

	private List<Tag> tags = new ArrayList<Tag>();

	private List<Picture> pictures = new ArrayList<Picture>();

	private Box box;

	private Date creationDate;

	private Set<User> owners = new HashSet<User>();

	private Set<User> wishers = new HashSet<User>();

	private String details;

	public Game() {
		id = ID++;
		creationDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNgh() {
		return ngh;
	}

	public void setNgh(String ngh) {
		this.ngh = ngh;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getMegaCount() {
		return megaCount;
	}

	public void setMegaCount(Long megaCount) {
		this.megaCount = megaCount;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void addProperty(Property property) {
		properties.add(property);
	}

	public void addTag(Tag tag) {
		tags.add(tag);
	}

	public void addPicture(Picture picture) {
		pictures.add(picture);
	}

	public List<Picture> getPictures() {
		return Collections.unmodifiableList(pictures);
	}

	public List<Property> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public List<Tag> getTags() {
		return Collections.unmodifiableList(tags);
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public Box getBox() {
		return box;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	public Set<User> getOwners() {
		return owners;
	}

	public void addOwner(User user) {
		owners.add(user);
	}

	public void addWisher(User user) {
		wishers.add(user);
	}

	public Set<User> getWishers() {
		return wishers;
	}

	public Picture getMainPicture() {
		return pictures.isEmpty() ? Picture.EMPTY : pictures.get(0);
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDetails() {
		return details;
	}

	public String getCollectionRank() {
		return "1st";
	}

	public int getNumOwners() {
		return owners.size();
	}

	public String getWishRank() {
		return "2nd";
	}

	public int getNumWishers() {
		return wishers.size();
	}

}
