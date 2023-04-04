package com.example.weatherapp;

import android.net.Uri;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by SharmaDiksha on 3/7/2023 2023
 */
public class WeatherServiceProvider {
    private static RequestQueue Rqueue;
    private static Weather weather_Obj;
    private static MainActivity mainActivity;

    private static final String TAG = "WeatherServiceProvider ";
    private static String weatherURL = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private static final String myAPIKey = "N2GTXXZJ9UELKCQVM9JQ5KYM3";

    public static void downloader(MainActivity mainActivityIn,
                                       String city, boolean fahrenheit) {

        mainActivity = mainActivityIn;

        Rqueue = Volley.newRequestQueue(mainActivity);

        String newURL = weatherURL + city;
        Uri.Builder buildURL = Uri.parse(newURL).buildUpon();

        buildURL.appendQueryParameter("lang", "en");
        buildURL.appendQueryParameter("unitGroup", (fahrenheit ? "us" : "metric"));
        buildURL.appendQueryParameter("key", myAPIKey);
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONObject> listener =
                response -> parseJSON(response.toString());

        Response.ErrorListener error =
                error1 -> mainActivity.updateData(null);

        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error);

        Rqueue.add(jsonObjectRequest);
    }


    private static void parseJSON(String s) {

        try {
            JSONObject jsonMainObject = new JSONObject(s);

            JSONArray jsonDays = jsonMainObject.getJSONArray("days");
            ArrayList<Days> daysArrayList = new ArrayList<Days>();
            for (int i=0;i<jsonDays.length();i++){
                JSONObject jsonDayObject = jsonDays.getJSONObject(i);
                long datetimeEpoch = jsonDayObject.getLong("datetimeEpoch");
                Double tempmax = jsonDayObject.getDouble("tempmax");
                Double tempmin = jsonDayObject.getDouble("tempmin");
                Double precipprob = jsonDayObject.getDouble("precipprob");
                Double uvindex = jsonDayObject.getDouble("uvindex");
                String description = jsonDayObject.getString("description");
                String icon = jsonDayObject.getString("icon");

                ArrayList<Hourly> hoursArrayList = new ArrayList<Hourly>();
                JSONArray jsonHours = jsonDayObject.getJSONArray("hours");
                for (int j=0;j<jsonHours.length();j++) {
                    JSONObject jsonHourObject = jsonHours.getJSONObject(j);
                    long datetimeEpochHour = jsonHourObject.getLong("datetimeEpoch");
                    Double tempHour = jsonHourObject.getDouble("temp");
                    String descriptionHour = jsonHourObject.getString("conditions");
                    String iconHour = jsonHourObject.getString("icon");

                    Hourly hour = new Hourly(datetimeEpochHour,tempHour,descriptionHour,iconHour);
                    hoursArrayList.add(hour);
                }

                Days day = new Days(datetimeEpoch,tempmax,tempmin,precipprob,uvindex,description,icon,hoursArrayList);
                daysArrayList.add(day);
            }

            int tzoffset = jsonMainObject.getInt("tzoffset");
            String address = jsonMainObject.getString("address");
            String timezone = jsonMainObject.getString("timezone");

            JSONObject jsonCurrentConditions = jsonMainObject.getJSONObject("currentConditions");
            long datetimeEpoch = jsonCurrentConditions.getLong("datetimeEpoch");
            Double temp = jsonCurrentConditions.getDouble("temp");
            Double feelslike = jsonCurrentConditions.getDouble("feelslike");
            Double humidity = jsonCurrentConditions.getDouble("humidity");
            Double windspeed = jsonCurrentConditions.getDouble("windspeed");
            Double windgust = windspeed;
            if (jsonCurrentConditions.get("windgust") == "null")
                windgust = jsonCurrentConditions.getDouble("windgust");
            Double winddir = jsonCurrentConditions.getDouble("winddir");
            Double visibility = jsonCurrentConditions.getDouble("visibility");
            Double cloudcover = jsonCurrentConditions.getDouble("cloudcover");
            Double uvindex = jsonCurrentConditions.getDouble("uvindex");
            String conditions = jsonCurrentConditions.getString("conditions");
            String icon = jsonCurrentConditions.getString("icon");
            long sunriseEpoch = jsonCurrentConditions.getLong("sunriseEpoch");
            long sunsetEpoch = jsonCurrentConditions.getLong("sunsetEpoch");

            CurrentData currentData = new CurrentData(datetimeEpoch,temp,feelslike,humidity,windgust
                    ,winddir,visibility,cloudcover,uvindex,conditions,icon,sunriseEpoch,sunsetEpoch, windspeed);

            weather_Obj = new Weather(address,timezone,tzoffset,daysArrayList, currentData);
            mainActivity.updateData(weather_Obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
