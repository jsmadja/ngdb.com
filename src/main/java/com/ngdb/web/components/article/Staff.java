package com.ngdb.web.components.article;

import com.ngdb.entities.article.Article;
import com.ngdb.services.SNK;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.*;

public class Staff {

    @Property
    @Parameter
    private Article article;

    @Property
    private String role;

    @Property
    private String employee;

    private Collection<String> roles;

    @Inject
    private SNK snk;

    @Property
    private com.ngdb.entities.Staff staff;

    @SetupRender
    public void init() {
        staff = snk.getStaffOf(article);
        if(staff != null) {
            roles = staff.roles();
        }
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public List<String> getEmployees() {
        List<String> employees = new ArrayList<String>(staff.employeesOfRole(role));
        Collections.sort(employees);
        return employees;
    }

}
