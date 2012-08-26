package com.ngdb.entities.reference;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@XmlRootElement(name = "origin")
@XmlAccessorType(FIELD)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Origin extends AbstractEntity implements Comparable<Origin>, Serializable {

    @Column(unique = true, nullable = false)
    private String title;

    public Origin() {
    }

    public Origin(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(Origin origin) {
        if (title == null || origin == null || origin.title == null) {
            return 0;
        }
        return title.compareToIgnoreCase(origin.title);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Origin) {
            return ((Origin) o).getId().equals(getId());
        }
        return false;
    }

}
