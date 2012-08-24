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

    private String imdbId;

    @Column(name = "mega_count")
    private Long megaCount;

    @OneToOne(fetch = FetchType.LAZY)
    private Box box;

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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    @Override
    public Class<?> getType() {
        return Game.class;
    }

    @Override
    public boolean isGame() {
        return true;
    }

}
