package com.example.weatherapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by SharmaDiksha on 3/2/2023
 */
public class Days implements Serializable {
    private final long datetime;
    private final Double max_Temperature,min_Temperature,precipation,uvindex;
    private final String description,icon;
    private final ArrayList<Hourly> hourly ;

    public Days(long datetime, Double max_Temperature, Double min_Temperature, Double precipation, Double uvindex, String description,
                String icon, ArrayList<Hourly> hourly) {
        this.datetime = datetime;
        this.max_Temperature = max_Temperature;
        this.min_Temperature = min_Temperature;
        this.precipation = precipation;
        this.uvindex = uvindex;
        this.description = description;
        this.icon = icon;
        this.hourly = hourly;
    }

    public long getDatetime() {
        return datetime;
    }
    public Double maxTemp() {
        return max_Temperature;
    }
    public Double minTemp() {
        return min_Temperature;
    }
    public Double getPrecipitation() {
        return precipation;
    }
    public Double getUvindex() {
        return uvindex;
    }
    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }
    public ArrayList<Hourly> getHours() {
        return hourly;
    }
}
