package com.ngdb.services;

import com.ngdb.entities.Employee;
import com.ngdb.entities.Participation;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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

    public List<Employee> getEmployees() {
        return session.createCriteria(Employee.class).addOrder(asc("employeeName")).list();
    }

    public Collection<Employee> getEmployeesOfRole(String role) {
        return new TreeSet<Employee>(session.createCriteria(Participation.class).add(eq("role", role)).setProjection(distinct(property("employee"))).list());
    }
}
