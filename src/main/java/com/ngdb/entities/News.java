package com.ngdb.entities;

import javax.persistence.*;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String language;

    @Column(nullable = false, length = 1024)
    private String description;

    @Override
    public String toString() {
        return description;
    }
}
