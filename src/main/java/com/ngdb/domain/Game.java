package com.ngdb.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	public Game() {
		id = ID++;
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

}
