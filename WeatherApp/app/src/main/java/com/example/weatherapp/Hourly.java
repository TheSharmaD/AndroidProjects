package com.example.weatherapp;

import java.io.Serializable;

/**
 * Created by SharmaDiksha on 3/2/2023
 */
public class Hourly implements Serializable {

    private final long datetime;
    private final Double temperature;
    private final String conditions;
    private final String icon;

    public Hourly(long datetimeEpoch, Double temp, String conditions, String icon) {
        this.datetime = datetimeEpoch;
        this.temperature = temp;
        this.conditions = conditions;
        this.icon = icon;
    }

    public long getDatetime() {
        return datetime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getConditions() {
        return conditions;
    }

    public String getIcon() {
        return icon;
    }
}
