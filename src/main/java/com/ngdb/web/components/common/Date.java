package com.ngdb.web.components.common;

import com.ngdb.web.services.infrastructure.CurrentUser;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.joda.time.DateMidnight;

public class Date {

    @Parameter(name = "value", allowNull = false, required = true)
    private String value;

    @Inject
    private CurrentUser user;

    public String getDate() {
        String dateAsString = value.split(" ")[0];
        if(user.isFrench()) {
            String[] split = dateAsString.split("-");
            return split[1]+"-"+split[2]+"-"+split[0];
        }
        return dateAsString;
    }

}
