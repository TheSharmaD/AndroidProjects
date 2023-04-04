package com.example.weatherapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SharmaDiksha on 3/7/2023 2023
 */
public class D_ViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView date, temperature, description, precipitation,
            uvIndex, morning_Temperature, afternoon_Temperature, evening_Temperature, night_Temperature,
            morning_TempText, afternoon_TempText, evening_TempText, night_TempText;
    public D_ViewHolder(View view) {
        super(view);
        date = view.findViewById(R.id.d_date);
        temperature = view.findViewById(R.id.d_temp);
        description = view.findViewById(R.id.d_desc);
        icon = view.findViewById(R.id.d_icon);
        precipitation = view.findViewById(R.id.d_prec);
        uvIndex = view.findViewById(R.id.d_uv);
        morning_Temperature = view.findViewById(R.id.d_morning);
        afternoon_Temperature = view.findViewById(R.id.d_noon);
        evening_Temperature = view.findViewById(R.id.d_evening);
        night_Temperature = view.findViewById(R.id.d_night);
        morning_TempText = view.findViewById(R.id.d_morning1);
        afternoon_TempText = view.findViewById(R.id.d_afternoon1);
        evening_TempText = view.findViewById(R.id.d_evening1);
        night_TempText = view.findViewById(R.id.d_night1);
    }
}