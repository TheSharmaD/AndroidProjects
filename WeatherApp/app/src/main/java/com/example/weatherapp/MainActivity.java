package com.example.weatherapp;
/**
 * Created by SharmaDiksha on 3/2/2023 203
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private TextView curr_DateTime, curr_Temperature, itfeelsLike, weather_Description, wind_Description
            , the_Humidity, uvIndex, morning_Temperature, afternoon_Temperature, evening_Temperature,
            night_Temperature, morning_TempText, afternoon_TempText, evening_TempText, night_TempText,
            sunrise, sunset, The_Visibility;
    private List<Hourly> hoursList = new ArrayList<>();
    private ArrayList<Days> daysArrayList = new ArrayList<>();
    private ImageView weatherIcon;
    private H_Adapter hoursAdapter;
    private String address;
    private SharedPreferences.Editor editor;

    private boolean f = true;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        curr_DateTime = findViewById(R.id.dateandtime);
        curr_Temperature = findViewById(R.id.temp);
        itfeelsLike = findViewById(R.id.itfeelslike);
        weather_Description = findViewById(R.id.desc);
        wind_Description = findViewById(R.id.thewinds);
        the_Humidity = findViewById(R.id.thehumidity);
        uvIndex = findViewById(R.id.uvindex);
        morning_Temperature = findViewById(R.id.morning);
        afternoon_Temperature = findViewById(R.id.noon);
        evening_Temperature = findViewById(R.id.eve);
        night_Temperature = findViewById(R.id.night);
        morning_TempText = findViewById(R.id.morning_sub);
        afternoon_TempText = findViewById(R.id.noon_sub);
        evening_TempText = findViewById(R.id.eve_sub);
        night_TempText = findViewById(R.id.night_sub);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        The_Visibility = findViewById(R.id.thevisibility);
        recyclerView = findViewById(R.id.mainrecyclerView);
        weatherIcon = findViewById(R.id.IconWeather);
        swiper = findViewById(R.id.swipe);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        if (!sharedPref.contains("FAHRENHEIT")) {
            editor.putBoolean("FAHRENHEIT", true);
            editor.apply();
        }
        if (!sharedPref.contains("ADDRESS")) {
            editor.putString("ADDRESS", "Chicago, Illinois");
            editor.apply();
        }

        f = sharedPref.getBoolean("FAHRENHEIT", true);
        address = sharedPref.getString("ADDRESS", "Chicago, Illinois");

        WeatherServiceProvider.downloader(this, address, f);

        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        recyclerView = findViewById(R.id.mainrecyclerView);
        hoursAdapter = new H_Adapter(hoursList, this, f);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(hoursAdapter);
        setData(true);
    }
    private void setData(Boolean flag) {

        int vis = !flag ? View.VISIBLE : View.INVISIBLE;
        curr_DateTime.setVisibility(vis);
        curr_Temperature.setVisibility(vis);
        itfeelsLike.setVisibility(vis);
        weather_Description.setVisibility(vis);
        wind_Description.setVisibility(vis);
        the_Humidity.setVisibility(vis);
        uvIndex.setVisibility(vis);
        morning_Temperature.setVisibility(vis);
        afternoon_Temperature.setVisibility(vis);
        evening_Temperature.setVisibility(vis);
        night_Temperature.setVisibility(vis);
        morning_TempText.setVisibility(vis);
        afternoon_TempText.setVisibility(vis);
        evening_TempText.setVisibility(vis);
        night_TempText.setVisibility(vis);
        sunrise.setVisibility(vis);
        sunset.setVisibility(vis);
        The_Visibility.setVisibility(vis);
        weatherIcon.setVisibility(vis);
        recyclerView.setVisibility(vis);
        if (flag) {
            setTitle(R.string.app_name);
        } else {
            setTitle(address);
        }
    }

    private boolean NetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private String FormatDate(long datetimeEpoch, int type) {
        Date dateTime = new Date(datetimeEpoch * 1000);
        SimpleDateFormat fullDate =
                new SimpleDateFormat("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
        SimpleDateFormat timeOnly = new SimpleDateFormat("h:mm a", Locale.getDefault());
        String fullDateStr = fullDate.format(dateTime);
        String timeOnlyStr = timeOnly.format(dateTime);
        return type == 1 ? fullDateStr : timeOnlyStr;
    }

    private String Directions(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }
    public int getIcon(String icon) {
        icon = icon.replace("-", "_");
        int iconID =
                getResources().getIdentifier(icon, "drawable", getPackageName());
        if (iconID == 0) {
            iconID = 233;
        }
        return iconID;
    }
    public void updateData(Weather weather) {

        swiper.setRefreshing(false);
        if (!NetworkConnection()) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            if (curr_DateTime.getText().toString().isEmpty()) {
                curr_DateTime.setText("No internet connection");
                curr_DateTime.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (weather == null) {
            Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            setData(true);
            editor.remove("ADDRESS");
            editor.apply();
            daysArrayList.clear();
            hoursList.clear();
            return;
        }

        setData(false);
        setTitle(weather.getAddress());
        CurrentData currentConditions = weather.getCurrentConditions();
        daysArrayList = weather.getDays();
        ArrayList<Hourly> curHours = daysArrayList.get(0).getHours();
        curr_DateTime.setText(String.format("%s", FormatDate(currentConditions.getDatetime(), 1)));
        curr_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                currentConditions.getTemperature()));
        itfeelsLike.setText(String.format("Feels Like: %.0f°" + (f ? "F" : "C"),
                currentConditions.getitFeelslike()));
        weather_Description.setText(String.format(Locale.getDefault(), "%s (%.0f%% clouds)", currentConditions.getConditions()
                , currentConditions.getCloudcover()));
        wind_Description.setText(String.format(Locale.getDefault(), "Winds: %s at %.0f %s gusting to %.0f %s", Directions(currentConditions.getWinddirections())
                , currentConditions.getWindspeed(), (f ? "mph" : "kmph"), currentConditions.getWindgust(), (f ? "mph" : "kmph")));
        the_Humidity.setText(String.format(Locale.getDefault(), "Humidity: %.0f%%", currentConditions.getHumidity()));
        uvIndex.setText(String.format(Locale.getDefault(), "UV Index: %.0f", currentConditions.getUvindex()));
        The_Visibility.setText(String.format(Locale.getDefault(), "Visibility: %.0f %s", currentConditions.getVisibility(),
                (f ? "mi" : "km")));
        morning_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                curHours.get(8).getTemperature()));
        afternoon_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                curHours.get(13).getTemperature()));
        evening_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                curHours.get(17).getTemperature()));
        night_Temperature.setText(String.format("%.0f°" + (f ? "F" : "C"),
                curHours.get(23).getTemperature()));
        sunrise.setText(String.format("Sunrise: %s", FormatDate(currentConditions.getSunrise(), 2)));
        sunset.setText(String.format("Sunset: %s", FormatDate(currentConditions.getSunset(), 2)));
        int iconId = getIcon(currentConditions.getIcon());
        weatherIcon.setImageResource(iconId);
        hoursList.clear();
        ArrayList<Hourly> newList = new ArrayList<>();
        List<Hourly> hoursStream = daysArrayList.get(0).getHours().stream().filter(x ->
                x.getDatetime() > currentConditions.getDatetime()).collect(Collectors.toList());
        newList.addAll(hoursStream);
        newList.addAll(daysArrayList.get(1).getHours());
        newList.addAll(daysArrayList.get(2).getHours());
        newList.addAll(daysArrayList.get(3).getHours());
        hoursList.addAll(newList);
        hoursAdapter.f = f;
        hoursAdapter.notifyItemInserted(hoursList.size());
        hoursAdapter.notifyDataSetChanged();
        recyclerView.setBackgroundColor(Color.argb(40, 255, 255, 255));
        editor.putString("ADDRESS", address);
        editor.apply();
    }
    private void refreshData() {
        if (!NetworkConnection()) {
            swiper.setRefreshing(false);
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            WeatherServiceProvider.downloader(this, address, f);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(0).setIcon(f ? R.drawable.units_f : R.drawable.units_c);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (!NetworkConnection()) {
            Toast.makeText(this, "This action can not be perform because of no internet connection", Toast.LENGTH_SHORT).show();
            return super.onOptionsItemSelected(item);
        }

        if (item.getItemId() == R.id.days) {
            if (daysArrayList.isEmpty()) {
                Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            Intent intent = new Intent(this, DailyWeatherActivity.class);
            intent.putExtra("DAYS", (Serializable) daysArrayList);
            intent.putExtra("UNITS", f);
            intent.putExtra("ADDRESS", address);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.converter) {
            if (daysArrayList.isEmpty()) {
                Toast.makeText(this, "Please Enter a Valid City Name", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }
            f = !f;
            item.setIcon(f ? R.drawable.units_f : R.drawable.units_c);
            editor.putBoolean("FAHRENHEIT", f);
            editor.apply();
            WeatherServiceProvider.downloader(this, address, f);
            swiper.setRefreshing(true);
            return true;
        } else if (item.getItemId() == R.id.location) {
            showLocationDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLocationDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        @SuppressLint("InflateParams") final View view = inflater.inflate(R.layout.dailog_box, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);

        builder.setPositiveButton("OK", (dialog, id) -> {

            if (!NetworkConnection()) {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            EditText et = view.findViewById(R.id.editTextTextPersonName);
            if (et.getText().toString().isEmpty()) {
                Toast.makeText(this, "City name can't be empty, Please enter valid city name.",
                        Toast.LENGTH_SHORT).show();
            } else {
                address = et.getText().toString();
                WeatherServiceProvider.downloader(this, address, f);
            }
        });
        builder.setNegativeButton("CANCEL", (dialog, id) -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().
                getColor(R.color.purple_500));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().
                getColor(R.color.purple_500));
    }
}