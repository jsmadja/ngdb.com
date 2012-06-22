package com.ngdb.entities.article.element;

import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.reference.Genre;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Genres {

	@ManyToMany
	@JoinTable(name = "GameGenres", inverseJoinColumns = { @JoinColumn(name = "genre_id") }, joinColumns = { @JoinColumn(name = "game_id") })
	private Set<Genre> genres;

	public boolean contains(Genre genre) {
		return genres.contains(genre);
	}

}
