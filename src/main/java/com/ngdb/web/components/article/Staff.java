package com.ngdb.web.components.article;

import com.ngdb.entities.Employee;
import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Article;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

import java.util.*;

public class Staff {

    @Property
    @Parameter
    private Article article;

    @Property
    private String role;

    @Property
    private Employee employee;

    private Collection<String> roles;

    @SetupRender
    public void init() {
        roles = getStaff().roles();
    }

    public com.ngdb.entities.Staff getStaff() {
        return article.getStaff();
    }

    public boolean getHasStaff() {
        return article.hasStaff();
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<Employee>(employees());
        Collections.sort(employees);
        return employees;
    }

    private Collection<Employee> employees() {
        return getStaff().employeesOfRole(role);
    }

    public String getComma() {
        return (getEmployees().indexOf(employee)+1) == getEmployees().size() ? "" : ", ";
    }

}
