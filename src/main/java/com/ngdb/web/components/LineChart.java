package com.ngdb.web.components;

import com.ngdb.web.misc.charts.Categories;
import com.ngdb.web.misc.charts.Series;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.got5.tapestry5.jquery.highcharts.components.AbstractHighCharts;

public class LineChart extends AbstractHighCharts {

    @Parameter(allowNull = false, required = true)
    private Categories categories;

    @Parameter(allowNull = false, required = true)
    private Series series;

    @Parameter(allowNull = false, required = true)
    private String title;

    public JSONObject getComponentOptions() {
        JSONObject titleObject = new JSONObject().
                put("text", title).
                put("x", -20);

        JSONObject xAxis = new JSONObject().
                put("categories", categories.asJSONArray());

        JSONObject yAxis = new JSONObject().
                put("plotLines",
                        new JSONArray(
                                new JSONObject().
                                        put("values", 0).
                                        put("width", 0).
                                        put("color", "#808080")));

        JSONObject legend = new JSONObject().
                put("layout", "vertical").
                put("align", "left").
                put("verticalAlign", "top").
                put("x", 450).
                put("y", 0).
                put("background", "#FFFFFF").
                put("floating", true);

        JSONObject high = new JSONObject().
                put("title", titleObject).
                put("series", series.asJSONArray()).
                put("legend", legend).
                put("xAxis", xAxis).
                put("yAxis", yAxis).
                put("chart", new JSONObject("renderTo", getClientId(), "type", "line"));
        return high;
    }

}