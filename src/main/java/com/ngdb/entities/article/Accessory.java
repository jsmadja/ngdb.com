package com.ngdb.entities.article;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accessory extends Article implements Serializable {

    @Override
    public boolean isGame() {
        return false;
    }

    @Override
    public boolean isHardware() {
        return false;
    }

    @Override
    public boolean isAccessory() {
        return true;
    }

    @Override
    public String getViewPage() {
        return "article/accessory/accessoryView";
    }
}
