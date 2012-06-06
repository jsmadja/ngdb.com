package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.ngdb.entities.article.element.Genres;
import com.ngdb.entities.reference.Box;
import com.ngdb.entities.reference.Genre;
import com.ngdb.entities.reference.Platform;
import com.ngdb.entities.reference.Publisher;

@XmlRootElement(name = "game")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Game extends Article {

	private String ngh;

	@OneToOne
	private Publisher publisher;

	@Column(name = "mega_count")
	private Long megaCount;

	@Embedded
	@XmlTransient
	private Genres genres;

	@OneToOne
	private Platform platform;

	@OneToOne
	private Box box;

	private String upc;

	public Game() {
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

	public Long getMegaCount() {
		return megaCount;
	}

	public void setMegaCount(Long megaCount) {
		this.megaCount = megaCount;
	}

	public Genres getGenres() {
		return genres;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public Box getBox() {
		return box;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public String getUpc() {
		return upc;
	}

	public boolean isOfGenre(Genre genre) {
		return genres.contains(genre);
	}

	@Override
	public Class<?> getType() {
		return Game.class;
	}

}
