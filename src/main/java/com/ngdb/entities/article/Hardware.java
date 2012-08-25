package com.ngdb.entities.article;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@XmlRootElement(name = "hardware")
public class Hardware extends Article {

    @Override
    public Class<?> getType() {
        return Hardware.class;
    }

    @Override
    public boolean isGame() {
        return false;
    }

    @Override
    public String getViewPage() {
        return "article/hardware/hardwareView";
    }

}
