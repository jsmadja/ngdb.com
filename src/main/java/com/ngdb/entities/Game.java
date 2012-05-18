package com.ngdb.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Game extends Article {

	public static final Game EMPTY = new Game();

	private String ngh;

	@OneToOne
	private Publisher publisher;

	@Column(name = "mega_count")
	private Long megaCount;

	@ManyToMany
	@JoinTable(name = "GameGenres", inverseJoinColumns = { @JoinColumn(name = "genre_id") }, joinColumns = { @JoinColumn(name = "game_id") })
	private Set<Genre> genres;

	@OneToOne
	private Platform platform;

	@OneToOne
	private Box box;

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

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
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

	public boolean isBuyable() {
		return getAvailableCopyCount() > 0;
	}

}
