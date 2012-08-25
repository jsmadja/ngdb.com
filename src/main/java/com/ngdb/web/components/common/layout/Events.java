package com.ngdb.web.components.common.layout;

import java.util.Collection;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.ngdb.entities.ActionLogger;
import com.ngdb.entities.article.ArticleAction;
import com.ngdb.web.pages.article.game.GameView;
import com.ngdb.web.pages.article.hardware.HardwareView;

public class Events {

    @InjectPage
    private GameView gameView;

    @InjectPage
    private HardwareView hardwareView;

    @Inject
    private ActionLogger actionLogger;

    @Property
    private ArticleAction update;

    public Collection<ArticleAction> getUpdates() {
        return actionLogger.listLastActions();
    }

}
