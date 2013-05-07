package com.ngdb.web.components.article;

import com.ngdb.entities.Participation;
import com.ngdb.entities.article.Article;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

public class Staff {

    @Property
    @Parameter
    private Article article;

    @Property
    private Participation participation;

    public com.ngdb.entities.Staff getStaff() {
        return article.getStaff();
    }

    public boolean getHasStaff() {
        return article.hasStaff();
    }

}
