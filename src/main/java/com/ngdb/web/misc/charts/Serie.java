package com.ngdb.web.misc.charts;

import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class Serie {

    private Collection<Object> values = new ArrayList<Object>();
    private String name;

    public Serie(String name) {
        this.name = name;
    }

    public void add(Object value) {
        this.values.add(value);
    }

    public JSONObject asJSONObject() {
        JSONArray data = new JSONArray();
        for (Object value : values) {
            data.put(value);
        }
        return new JSONObject().put("name", name).put("data", data);
    }
}
