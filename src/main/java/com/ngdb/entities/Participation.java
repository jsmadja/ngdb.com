package com.ngdb.entities;

import com.ngdb.entities.article.Article;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String employee;

    private String role;

    @ManyToOne(optional = false, fetch = LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Article article;

    Participation() {}

    public Participation(String employee, String role, Article article) {
        this.employee = employee;
        this.role = role;
        this.article = article;
    }

    public String getEmployee() {
        return employee;
    }

    public String getRole() {
        return role;
    }

    public Article getArticle() {
        return article;
    }

    public void delete() {
        this.employee = null;
    }

    public boolean hasRole(String role) {
        return this.role.equalsIgnoreCase(role);
    }
}
