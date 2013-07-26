package com.ngdb.entities.article;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Game extends Article implements Serializable {

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String ngh;

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String imdbId;

    @Column(name = "mega_count")
    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
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
    public boolean isGame() {
        return true;
    }

    @Override
    public boolean isHardware() {
        return false;
    }

    @Override
    public boolean isAccessory() {
        return false;
    }

    @Override
    public String getViewPage() {
        return "article/game/gameView";
    }

    @Override
    public String getUpdatePage() {
        return "article/game/gameUpdate";
    }

    public boolean isAES() {
        return getPlatformShortName().equalsIgnoreCase("AES");
    }

    public boolean isJapanese() {
        return getOriginTitle().equalsIgnoreCase("Japan");
    }
}
