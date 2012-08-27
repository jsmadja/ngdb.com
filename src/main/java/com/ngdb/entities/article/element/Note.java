package com.ngdb.entities.article.element;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note extends AbstractEntity implements Comparable<Note> {

    private String name;

    private String text;

    @ManyToOne
    private Article article;

    Note() {
    }

    public Note(String name, String text, Article article) {
        this.article = article;
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean hasName(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    @Override
    public int compareTo(Note note) {
        return name.compareToIgnoreCase(note.name);
    }

    public boolean hasValue(String value) {
        return this.text.equalsIgnoreCase(value);
    }

}
