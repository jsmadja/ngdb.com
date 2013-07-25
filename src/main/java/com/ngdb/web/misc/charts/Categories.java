package com.ngdb.web.misc.charts;

import org.apache.tapestry5.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

public class Categories {

    private Collection<String> categories = new ArrayList<String>();

    public JSONArray asJSONArray() {
        JSONArray array = new JSONArray();
        for (String category : categories) {
            array.put(category);
        }
        return array;
    }

    public void add(String category) {
        this.categories.add(category);
    }
}
