package com.ngdb.entities.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.reference.Box;

@Entity
@XmlRootElement(name = "game")
@XmlAccessorType(XmlAccessType.FIELD)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game extends Article {

	private String ngh;

	@Column(name = "mega_count")
	private Long megaCount;

	@OneToOne(fetch = FetchType.LAZY)
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

	public Long getMegaCount() {
		return megaCount;
	}

	public void setMegaCount(Long megaCount) {
		this.megaCount = megaCount;
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

	@Override
	public Class<?> getType() {
		return Game.class;
	}

}
