package com.ngdb.entities.article;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game extends Article implements Serializable {

    private String ngh;

    private String imdbId;

    @Column(name = "mega_count")
    private Long megaCount;

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

    @Override
    public String getViewPage() {
        return "article/game/gameView";
    }

}
