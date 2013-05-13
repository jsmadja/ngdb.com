package com.ngdb.services;

import com.ngdb.entities.ArticleFactory;
import com.ngdb.entities.Participation;
import com.ngdb.entities.Staff;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.article.Game;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.transform.ResultTransformer;

import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static org.hibernate.criterion.Order.asc;
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

    public Collection<String> getEmployeesOfRole(String role) {
        return new TreeSet<String>(session.createCriteria(Participation.class).add(eq("role", role)).setProjection(distinct(property("employee"))).list());
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

    public List<Participation> getParticipationsOf(String employee) {
        return session.createCriteria(Participation.class).add(eq("employee", employee)).list();
    }

    public Collection<String> orderStaffByEmployeeCount(Article article) {
        return session.createSQLQuery("SELECT role FROM Participation WHERE article_id = "+article.getId()+" GROUP BY role ORDER BY count(role) ASC;").list();
    }
}
