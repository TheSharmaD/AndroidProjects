package com.example.weatherapp;

import java.util.ArrayList;

/**
 * Created by SharmaDiksha on 3/7/2023 2023
 */
public class Weather {

    private final String address,timezone;
    private final int offset;
    private final ArrayList<Days> days;
    private final CurrentData currentData;

    public Weather(String address, String timezone, int tzoffset, ArrayList<Days> days, CurrentData currentData) {
        this.address = address;
        this.timezone = timezone;
        this.offset = tzoffset;
        this.days = days;
        this.currentData = currentData;
    }

    public String getAddress() {
        return address;
    }

    String getTimezone() {
        return timezone;
    }

    int getoffset() {
        return offset;
    }

    public ArrayList<Days> getDays() {
        return days;
    }

    public CurrentData getCurrentConditions() {
        return currentData;
    }
}
