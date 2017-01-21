package com.ngdb.web.components.common.layout;

import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.services.Cacher;
import com.ngdb.web.services.infrastructure.CurrentUser;
import com.ngdb.web.services.infrastructure.PictureService;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.ThreadLocale;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class Menu {

    @Inject
    private ActionLogger actionLogger;

    @Property
    private ArticleAction update;

    @Inject
    private Messages messages;

    private static boolean goEmpty = false;

    @Inject
    private Request request;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Inject
    private PictureService pictureService;

    @Inject
    private Cacher cacher;

    @Inject
    private CurrentUser currentUser;

    @Inject
    private ThreadLocale threadLocale;

    public JSONObject getParams() {
        return new JSONObject("width", "750", "modal", "false", "dialogClass", "dialog-edition", "zIndex", "1002");
    }

    public Collection<ArticleAction> getUpdates() {
        if (goEmpty) {
            return new ArrayList<ArticleAction>();
        } else {
            return actionLogger.listLastActions();
        }
    }

    public String getUpdateMessage() {
        return messages.get("whatsnew." + update.getMessage());
    }

    public String getLastUpdateDate() {
        Locale locale = threadLocale.getLocale();
        return update.getLastUpdateDate(locale);
    }

}
