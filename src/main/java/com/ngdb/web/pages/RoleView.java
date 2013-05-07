package com.ngdb.web.pages;

import com.ngdb.entities.Employee;
import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Article;
import com.ngdb.services.SNK;
import com.sun.jmx.snmp.SnmpUsmKeyHandler;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import java.util.Collection;
import java.util.List;

public class RoleView {

    @Property
    private Employee employee;

    @Property
    private Participation participation;

    @Property
    private String role;

    @Inject
    private SNK snk;

    public void onActivate(String role) {
        this.role = role;
    }

    public Collection<Employee> getEmployees() {
        return snk.getEmployeesOfRole(role);
    }

}
