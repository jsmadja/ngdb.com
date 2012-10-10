package com.ngdb.web.pages.user;

import com.ngdb.entities.user.User;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

public class UserView {

    @Property
    private User user;

    @Inject
    private Messages messages;

    void onActivate(User user) {
        this.user = user;
    }

    public String getCollectionLabel() {
        return messages.format("member.collectionLabel", user.getLogin());
    }

    public String getShopLabel() {
        return messages.format("member.shopLabel", user.getLogin());
    }

    public String getWishLabel() {
        return messages.format("member.wishLabel", user.getLogin());
    }

}
