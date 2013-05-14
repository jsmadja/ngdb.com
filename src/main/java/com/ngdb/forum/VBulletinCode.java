package com.ngdb.forum;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.services.infrastructure.PictureService;

import java.util.Collection;

import static java.lang.String.format;

public class VBulletinCode extends ForumCode {

    public VBulletinCode(Collection<ShopItem> shopItems, PictureService pictureService) {
        super(shopItems, pictureService);
    }

    @Override
    protected String smallText(String value) {
        return format("[SIZE=\"1\"]%s[/SIZE]", value);
    }

    @Override
    protected String url(String url, String value) {
        return format("[URL=%s]%s[/URL]", url, value);
    }

    @Override
    protected String green(String value) {
        return format("[COLOR=SeaGreen]%s[/COLOR]", value);
    }

    @Override
    protected String red(String value) {
        return format("[COLOR=Red]%s[/COLOR]", value);
    }

    @Override
    protected String bold(String value) {
        return format("[B]%s[/B]", value);
    }

    @Override
    protected String italic(String value) {
        return format("[I]%s[/I]", value);
    }

    @Override
    protected String img(String value) {
        return format("[IMG]%s[/IMG]", value);
    }

}
