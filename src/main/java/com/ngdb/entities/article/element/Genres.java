package com.ngdb.entities.article.element;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.ngdb.entities.article.vo.Genre;

@Embeddable
public class Genres {

	@ManyToMany
	@JoinTable(name = "GameGenres", inverseJoinColumns = { @JoinColumn(name = "genre_id") }, joinColumns = { @JoinColumn(name = "game_id") })
	private Set<Genre> genres;

	public boolean contains(Genre genre) {
		return genres.contains(genre);
	}

}
