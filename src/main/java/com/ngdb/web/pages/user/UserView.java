package com.ngdb.web.pages.user;

import com.ngdb.entities.Market;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.entities.shop.Wish;
import com.ngdb.entities.user.User;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import java.util.List;
import java.util.Set;

public class UserView {

    @Persist
    private User user;

    @Property
    private Wish wish;

    @Property
    private Article article;

    @Property
    private User seller;

    @Inject
    private Market market;

    @Inject
    private WishBox wishBox;

    void onActivate(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Set<User> getSellers() {
        return market.findSellersOf(wish.getArticle());
    }

    public List<Wish> getWishes() {
        return wishBox.findAllOf(user);
    }

}
