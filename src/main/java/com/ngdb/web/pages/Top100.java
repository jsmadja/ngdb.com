package com.ngdb.web.pages;

import com.ngdb.entities.Registry;
import com.ngdb.entities.Top100Item;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.reference.ReferenceService;
import com.ngdb.web.model.Top100List;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Top100 {

    @Inject
    private Registry registry;

    @Property
    private Top100Item topItem;

    @Inject
    private ReferenceService referenceService;

    @Persist
    @Property
    private Integer top100;

    @Persist
    private Integer currentTop100;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @InjectComponent
    private Zone top100Zone;

    @Inject
    private Messages messages;

    @Inject
    private BeanModelSource beanModelSource;

    @Persist
    @Property
    private BeanModel<Top100Item> model;

    private final static Collection<String> tops = new ArrayList<String>();

    @Persist
    private long oldCount;

    static {
        tops.add("Top 100 - Collection - AES");
        tops.add("Top 100 - Collection - MVS");
        tops.add("Top 100 - Collection - NGCD");
        tops.add("Top 100 - Collection - NGP");
        tops.add("Top 100 - Collection - NGPC");
        tops.add("Top 100 - Collection - H64");
    }

    void onActivate() {
        oldCount = Long.MIN_VALUE;
        currentTop100 = 0;
    }

    @SetupRender
    void init() {
        model = beanModelSource.createDisplayModel(Top100Item.class, messages);
        model.get("title").label(messages.get("common.Title")).sortable(true);
        model.get("originTitle").label(messages.get("common.Origin")).sortable(true);
        model.get("rank").label(messages.get("common.Rank")).sortable(true);
        model.get("count").label(messages.get("common.Count")).sortable(true);
        model.include("rank", "title", "count", "originTitle");
        model.reorder("rank", "count", "title", "originTitle");
    }

    public Collection<Top100Item> getTopItems() {
        Collection<Top100Item> top100ItemList = new ArrayList<Top100Item>();
        switch (currentTop100) {
            case 0:
                top100ItemList = registry.findTop100OfGamesInCollectionByPlatform(referenceService.findPlatformByName("AES"));
                break;
            case 1:
                top100ItemList = registry.findTop100OfGamesInCollectionByPlatform(referenceService.findPlatformByName("MVS"));
                break;
            case 2:
                top100ItemList = registry.findTop100OfGamesInCollectionByPlatform(referenceService.findPlatformByName("NGCD"));
                break;
            case 3:
                top100ItemList = registry.findTop100OfGamesInCollectionByPlatform(referenceService.findPlatformByName("NGP"));
                break;
            case 4:
                top100ItemList = registry.findTop100OfGamesInCollectionByPlatform(referenceService.findPlatformByName("NGPC"));
                break;
            case 5:
                top100ItemList = registry.findTop100OfGamesInCollectionByPlatform(referenceService.findPlatformByName("H64"));
                break;
        }
        return top100ItemList;
    }

    public SelectModel getTop100List() {
        return new Top100List(tops);
    }

    @OnEvent(component = "top100", value = EventConstants.VALUE_CHANGED)
    public void onSelectFromTop100(int id) {
        this.currentTop100 = id;
        ajaxResponseRenderer.addRender(top100Zone);
    }

}