package com.ngdb.web.components.article;

import com.ngdb.entities.Museum;
import com.ngdb.entities.WishBox;
import com.ngdb.entities.article.Article;
import com.ngdb.web.Filter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class ArticleStats {

    @Property
    @Parameter
    private Article article;

    @Inject
    private Museum museum;

    @Inject
    private WishBox wishBox;

    public String getCollectionRank() {
        return asRankString(museum.getRankOf(article));
    }

    public String getWishRank() {
        return asRankString(wishBox.getRankOf(article));
    }

    public int getNumAvailableCopy() {
        return article.getAvailableCopyCount();
    }

    private String asRankString(int value) {
        if (value == Integer.MAX_VALUE) {
            return "N/A";
        }
        int hundredRemainder = value % 100;
        int tenRemainder = value % 10;
        if (hundredRemainder - tenRemainder == 10) {
            return value + "th";
        }
        switch (tenRemainder) {
            case 1:
                return value + "st";
            case 2:
                return value + "nd";
            case 3:
                return value + "rd";
            default:
                return value + "th";
        }
    }

    public boolean isBuyable() {
        return getNumAvailableCopy() > 0;
    }

    public String getByArticle() {
        return Filter.byArticle.name();
    }

}
