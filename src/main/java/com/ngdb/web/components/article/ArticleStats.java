package com.ngdb.web.components.article;

import com.ngdb.entities.Museum;
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

    public String getCollectionRank() {
        return asRankString(museum.getRankOf(article));
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

    public String getByArticle() {
        return Filter.byArticle.name();
    }

}
