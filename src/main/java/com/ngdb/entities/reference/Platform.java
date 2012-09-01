package com.ngdb.entities.reference;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Platform extends AbstractEntity implements Comparable<Platform> {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name="short_name")
    private String shortName;

    public Platform() {
    }

    public Platform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Platform platform) {
        if (name == null || platform.name == null) {
            return 0;
        }
        return name.compareToIgnoreCase(platform.name);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
