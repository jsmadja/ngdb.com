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

    @ManyToOne(optional = false, fetch = LAZY, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    private Employee employee;

    private String role;

    @ManyToOne(optional = false, fetch = LAZY)
    private Article article;

    Participation() {}

    public Participation(Employee employee, String role, Article article) {
        this.employee = employee;
        employee.addParticipation(this);
        this.role = role;
        this.article = article;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getRole() {
        return role;
    }

    public Article getArticle() {
        return article;
    }

    public String getEmployeeName() {
        return employee.getEmployeeName();
    }

    public void delete() {
        this.employee = null;
    }

    public boolean hasRole(String role) {
        return this.role.equalsIgnoreCase(role);
    }
}
