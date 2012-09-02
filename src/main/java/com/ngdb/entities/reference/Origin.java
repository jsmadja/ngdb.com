package com.ngdb.entities.reference;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Origin implements Comparable<Origin> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
            return ((Origin) o).id.equals(id);
        }
        return false;
    }

    public Long getId() {
        return id;
    }
}
