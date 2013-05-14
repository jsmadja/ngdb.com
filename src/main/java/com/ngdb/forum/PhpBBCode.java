package com.ngdb.forum;

import com.ngdb.entities.shop.ShopItem;
import com.ngdb.web.services.infrastructure.PictureService;

import java.util.Collection;

import static java.lang.String.format;

public class PhpBBCode extends ForumCode {

    public PhpBBCode(Collection<ShopItem> shopItems, PictureService pictureService) {
        super(shopItems, pictureService);
    }

    @Override
    protected String smallText(String value) {
        return format("[size=80]%s[/size]", value);
    }

    @Override
    protected String url(String url, String value) {
        return format("[url=%s]%s[/url]", url, value);
    }

    @Override
    protected String green(String value) {
        return format("[color=green]%s[/color]", value);
    }

    @Override
    protected String red(String value) {
        return format("[color=red]%s[/color]", value);
    }

    @Override
    protected String bold(String value) {
        return format("[b]%s[/b]", value);
    }

    @Override
    protected String italic(String value) {
        return format("[i]%s[/i]", value);
    }

    @Override
    protected String img(String value) {
        return format("[img]%s[/img]", value);
    }
}
