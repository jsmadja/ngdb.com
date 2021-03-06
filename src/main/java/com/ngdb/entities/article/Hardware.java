package com.ngdb.entities.article;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hardware extends Article {

    @Override
    public boolean isGame() {
        return false;
    }

    @Override
    public boolean isHardware() {
        return true;
    }

    @Override
    public boolean isAccessory() {
        return false;
    }

    @Override
    public String getViewPage() {
        return "article/hardware/hardwareView";
    }

    @Override
    public String getUpdatePage() {
        return "article/hardware/hardwareUpdate";
    }

}
