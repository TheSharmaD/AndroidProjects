package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SharmaDiksha on 3/7/2023 2023
 */
public class H_ViewHolder extends RecyclerView.ViewHolder {

    public TextView day, time, temperature, description;
    public ImageView icon;

    public H_ViewHolder(View view) {
        super(view);
        day = view.findViewById(R.id.h_day);
        time = view.findViewById(R.id.h_time);
        temperature = view.findViewById(R.id.h_temp);
        description = view.findViewById(R.id.h_desc);
        icon = view.findViewById(R.id.h_icon);
    }
}
