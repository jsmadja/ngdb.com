package com.ngdb.web.components.common;

import org.apache.tapestry5.annotations.Parameter;

public class Date {

    @Parameter(name = "value", allowNull = false, required = true)
    private String value;

    public String getDate() {
        return value.split(" ")[0];
    }

}
