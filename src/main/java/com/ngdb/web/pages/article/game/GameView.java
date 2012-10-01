package com.ngdb.web.pages.article.game;

import com.ngdb.BarcodeUtil;
import com.ngdb.entities.article.Game;
import com.ngdb.entities.article.element.Tag;
import com.ngdb.entities.user.User;
import com.ngdb.web.Filter;
import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class GameView {

    @Persist("entity")
    private Game game;

    @Property
    private Tag tag;

    @Property
    private String value;

    @Inject
    private CurrentUser currentUser;

    public void onActivate(Game game) {
        this.game = game;
    }

    public void onActivate(Game game, String suffix) {
        onActivate(game);
    }

    public Game onPassivate() {
        return game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getDetails() {
        return StringEscapeUtils.unescapeHtml(game.getDetails());
    }

    public String getByNgh() {
        return Filter.byNgh.name();
    }

    public String getByOrigin() {
        return Filter.byOrigin.name();
    }

    public String getByPlatform() {
        return Filter.byPlatform.name();
    }

    public String getByPublisher() {
        return Filter.byPublisher.name();
    }

    public String getByReleaseDate() {
        return Filter.byReleaseDate.name();
    }

    public String getNgh() {
        return StringUtils.isNumeric(game.getNgh()) ? "NGH " + game.getNgh() : game.getNgh();
    }

    public User getUser() {
        return currentUser.getUser();
    }

    public String getUpc() {
        return BarcodeUtil.toBarcodeBase64Image(game.getUpc());
    }

    public boolean getShowUpc() {
        return game.getUpc() != null && !"0000000000000".equals(game.getUpc());
    }

}
