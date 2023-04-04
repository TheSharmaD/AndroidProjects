package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SharmaDiksha on 3/2/2023
 */
public class D_Adapter extends RecyclerView.Adapter<D_ViewHolder> {
    private final List<Days> daysList;
    private final DailyWeatherActivity days;
    private boolean f;

    public D_Adapter(List<Days> daysList, DailyWeatherActivity daysAct, boolean f) {
        this.daysList = daysList;
        this.days = daysAct;
        this.f = f;
    }
    private String getFormattedDate(long datetimeEpoch) {
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat dayDate = new SimpleDateFormat(" EEEE, MM/dd", Locale.getDefault());
        String dayDateStr = dayDate.format(dateTime);
        return dayDateStr;
    }
    @NonNull
    @Override
    public D_ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daily, parent, false);
        return new D_ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull D_ViewHolder holder, int position) {

        Days day = daysList.get(position);
        holder.date.setText(getFormattedDate(day.getDatetime()));
        holder.icon.setImageResource(days.getImageIcon(day.getIcon()));
        holder.description.setText(day.getDescription());
        holder.temperature.setText(String.format("%.0f°" + (f ? "F" : "C") + "/%.0f°" + (f ? "F" : "C"),
                day.maxTemp(), day.minTemp()));
        holder.precipitation.setText(String.format(Locale.getDefault(), "(%.0f%% precip.)", day.getPrecipitation()));
        holder.uvIndex.setText(String.format(Locale.getDefault(), "UV Index: %.0f", day.getUvindex()));
        holder.morning_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                day.getHours().get(8).getTemperature()));
        holder.afternoon_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                day.getHours().get(13).getTemperature()));
        holder.evening_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                day.getHours().get(17).getTemperature()));
        holder.night_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                day.getHours().get(22).getTemperature()));
    }
    @Override
    public int getItemCount() {
        return daysList.size();
    }

}
