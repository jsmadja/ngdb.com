package com.ngdb.web.pages;

import com.ngdb.base.ParticipationInGame;
import com.ngdb.entities.article.Article;
import com.ngdb.services.SNK;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import java.util.Collection;

import static com.google.common.collect.Lists.transform;
import static com.ngdb.base.ParticipationInGame.toParticipationInGame;

public class EmployeeView {

    @Persist
    @Property
    private String employee;

    @Property
    private ParticipationInGame participation;

    @Inject
    private BeanModelSource beanModelSource;

    @Inject
    private Messages messages;

    @Inject
    private SNK snk;

    public void onActivate(String employee) {
        this.employee = employee;
    }

    public BeanModel<ParticipationInGame> getModel() {
        BeanModel<ParticipationInGame> displayModel = beanModelSource.createDisplayModel(ParticipationInGame.class, messages);
        displayModel.add("article").sortable(true);
        displayModel.include("article", "role", "date");
        return displayModel;
    }

    public Collection<ParticipationInGame> getParticipations() {
        return transform(snk.getParticipationsOf(employee), toParticipationInGame);
    }

    public String getViewPage() {
        return participation.getArticle().getViewPage();
    }

    public Article getArticle() {
        return participation.getArticle();
    }

}
