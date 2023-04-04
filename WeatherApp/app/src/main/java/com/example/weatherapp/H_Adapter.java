package com.example.weatherapp;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SharmaDiksha on 3/2/2023
 */
public class H_Adapter extends RecyclerView.Adapter<H_ViewHolder> {

    public boolean f;
    private final List<Hourly> hourlyList;
    private final MainActivity mainAct;

    public H_Adapter(List<Hourly> hourly, MainActivity mainAct, boolean fahrenheit) {
        this.hourlyList = hourly;
        this.mainAct = mainAct;
        this.f = fahrenheit;
    }
    private String getFormattedDate(long datetimeEpoch, int type) {
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
        SimpleDateFormat day = new SimpleDateFormat("EEEE", Locale.getDefault());
        String timeOnlyStr = timeOnly.format(dateTime);
        String dayStr = day.format(dateTime);
        dayStr = DateUtils.isToday(datetimeEpoch * 1000) ? "Today" : dayStr;
        return type == 1 ? dayStr : timeOnlyStr;
    }
    @NonNull
    @Override
    public H_ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        return new H_ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull H_ViewHolder holder, int position) {

        Hourly hour = hourlyList.get(position);
        holder.day.setText(getFormattedDate(hour.getDatetime(), 1));
        holder.time.setText(getFormattedDate(hour.getDatetime(), 2));
        holder.temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                hour.getTemperature()));
        holder.description.setText(hour.getConditions());
        holder.icon.setImageResource(mainAct.getIcon(hour.getIcon()));
    }
    @Override
    public int getItemCount() {
        return hourlyList.size();
    }
}
