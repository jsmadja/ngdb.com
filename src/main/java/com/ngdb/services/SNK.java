package com.ngdb.services;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Employee;
import com.ngdb.entities.Participation;
import com.ngdb.entities.Staff;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static org.hibernate.criterion.Projections.distinct;
import static org.hibernate.criterion.Projections.property;
import static org.hibernate.criterion.Restrictions.eq;

public class SNK {

    @Inject
    private Session session;

    @Inject
    private ArticleFactory articleFactory;

    public SNK() {
        super();
    }

    public Collection<Employee> getEmployeesOfRole(String role) {
        return new TreeSet<Employee>(session.createCriteria(Participation.class).add(eq("role", role)).setProjection(distinct(property("employee"))).list());
    }

    public Staff getStaffOf(Article article) {
        if (article.hasStaff() || !article.isGame()) {
            return article.getStaff();
        }
        String ngh = ((Game) article).getNgh();
        List<Game> games = articleFactory.findAllGamesByNgh(ngh);
        for (Game game : games) {
            if (game.hasStaff()) {
                return game.getStaff();
            }
        }
        return null;
    }
}
