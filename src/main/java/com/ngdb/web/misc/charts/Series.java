package com.ngdb.web.misc.charts;

import org.apache.tapestry5.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;

public class Series {

    private Collection<Serie> series = new ArrayList<Serie>();

    public JSONArray asJSONArray() {
        JSONArray array = new JSONArray();
        for (Serie serie : series) {
            array.put(serie.asJSONObject());
        }
        return array;
    }

    public void add(Serie serie) {
        this.series.add(serie);
    }
}
