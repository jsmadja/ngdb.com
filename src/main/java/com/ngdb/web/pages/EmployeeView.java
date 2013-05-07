package com.ngdb.web.pages;

import com.ngdb.entities.Participation;
import com.ngdb.services.SNK;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import java.util.List;

public class EmployeeView {

    @Property
    private String employee;

    @Property
    private Participation participation;

    @Property
    private BeanModel<Participation> model;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private SNK snk;

    @Property
    private List<Participation> participations;

    public void onActivate(String employee) {
        this.employee = employee;
        this.participations = snk.getParticipationsOf(employee);
        model = beanModelSource.createDisplayModel(Participation.class, messages);
        model.addEmpty("article").sortable(true);
        model.addEmpty("date").sortable(true);
        model.include("article", "role", "date");
    }

}
