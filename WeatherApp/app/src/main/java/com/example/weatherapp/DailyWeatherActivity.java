package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SharmaDiksha on 3/2/2023
 */
public class DailyWeatherActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private boolean f;
    private D_Adapter daysAdapter;
    private List<Days> daysList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailyweather);

        Intent intent = getIntent();
        if (intent.hasExtra("DAYS")) {
            daysList = (ArrayList<Days>) intent.getSerializableExtra( "DAYS");
            f = intent.getBooleanExtra("UNITS", false);
            setTitle(intent.getStringExtra("ADDRESS") + " 15 Day");
        }
        recyclerView = findViewById(R.id.RecyclerViewD);
        daysAdapter = new D_Adapter(daysList, this, f);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(daysAdapter);
        daysAdapter.notifyItemInserted(daysList.size());
    }
    public int getImageIcon(String icon) {
        icon = icon.replace("-", "_");
        int iconID =
                getResources().getIdentifier(icon, "drawable", getPackageName());
        if (iconID == 0) {
            iconID = 233;
        }
        return iconID;
    }
}