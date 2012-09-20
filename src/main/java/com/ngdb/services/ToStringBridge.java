package com.ngdb.services;

import org.hibernate.search.bridge.StringBridge;

public class ToStringBridge implements StringBridge {

    @Override
    public String objectToString(Object object) {
        if(object == null) {
            return null;
        }
        return object.toString();
    }
}