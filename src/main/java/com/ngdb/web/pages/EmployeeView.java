package com.ngdb.web.pages;

import com.ngdb.entities.Employee;
import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Article;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

public class EmployeeView {

    @Property
    private Employee employee;

    @Property
    private Participation participation;

    @Property
    private BeanModel<Participation> model;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    public void onActivate(Employee employee) {
        this.employee = employee;
        model = beanModelSource.createDisplayModel(Participation.class, messages);
        model.addEmpty("article").sortable(true);
        model.addEmpty("date").sortable(true);
        model.include("article", "role", "date");
    }

}
