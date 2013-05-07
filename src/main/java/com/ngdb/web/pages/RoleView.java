package com.ngdb.web.pages;

import com.ngdb.entities.Participation;
import com.ngdb.services.SNK;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.Collection;
import java.util.List;

public class RoleView {

    @Property
    private String employee;

    @Property
    private Participation participation;

    @Property
    private String role;

    @Inject
    private SNK snk;

    public void onActivate(String role) {
        this.role = role;
    }

    public Collection<String> getEmployees() {
        return snk.getEmployeesOfRole(role);
    }

    public List<Participation> getParticipations() {
        return snk.getParticipationsOf(employee);
    }

}
