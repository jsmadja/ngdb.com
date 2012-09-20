package com.ngdb.entities.article.element;

import com.ngdb.entities.AbstractEntity;
import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Indexed
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Note extends AbstractEntity implements Comparable<Note> {

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
    private String name;

    @Field(analyzer = @Analyzer(definition = "noaccent"), store = Store.YES)
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
        int comp = name.compareToIgnoreCase(note.name);
        if(comp != 0) {
            return comp;
        }
        return text.compareToIgnoreCase(note.text);
    }

    public boolean hasValue(String value) {
        return this.text.equalsIgnoreCase(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (name != null ? !name.equals(note.name) : note.name != null) return false;
        if (text != null ? !text.equals(note.text) : note.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
