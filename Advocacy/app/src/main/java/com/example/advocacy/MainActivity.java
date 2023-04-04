package com.example.advocacy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final ArrayList<AAOfficial> array_officials = new ArrayList<>();
    private static final String TAG = "",
            googleCivic_API = "https://www.googleapis.com/civicinfo/v2/representatives?key=AIzaSyDJ6w5Uw96PUt6-rt6a0ORPda37OUwK1-A&address=";

    // key AIzaSyDJ6w5Uw96PUt6-rt6a0ORPda37OUwK1-A
    private static final int LOCATION_REQUEST = 111;
    static RecyclerView recycler_official;
    private static String address_to_add = "";
    public boolean error_location = false;
    private String link_api = "";
    private String state = "KS";
    static TextView topbar;
    private boolean internet_connection = true;
    private FusedLocationProviderClient mFusedLocationClient;
    private String loc = "Unknown";

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: " + savedInstanceState.getString("api"));
        link_api = savedInstanceState.getString("api");
        AADownloder.download(this, link_api);
    }
    public void connection(){
        internet_connection = internet_connection();
        if (!internet_connection) {
            setTitle("Know Your Government");
            noInternet();
        }
    }
    public void mFusedLocationClient(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        determining_the_location();}

    public void setTitle(String s) {
        topbar.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler_official = findViewById(R.id.recycler_main);
        topbar = findViewById(R.id.topbar_main);
        recycler_official = findViewById(R.id.recycler_main);
        AAAdapter officialAdapter = new AAAdapter(array_officials, this);
        recycler_official.setAdapter(officialAdapter);
        recycler_official.setLayoutManager(new LinearLayoutManager(this));
        setTitle(getString(R.string.enter_loc));
        array_officials.clear();
        connection();
        mFusedLocationClient();
    }


    public static String getAddress() {
        return topbar.getText().toString();
    }

    //OnCLick Function
    public void onClickMainMenuItem(MenuItem i) {
        internet_connection = internet_connection();
        if (internet_connection) {
            if (i.getItemId() == R.id.about_menu) {
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
            } else if (i.getItemId() == R.id.loaction_menu) {
                dialogue_for_location();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_network), Toast.LENGTH_SHORT).show();
            noInternet();
        }
    }

    public void official_addition_of_array(AAOfficial o) {
        array_officials.add(o);
    }

    public void URL(String a) {
        address_to_add = a;
        address_to_add = address_to_add.replace(" ", "%20");
        changeStateToInitial(address_to_add);
        address_to_add = address_to_add.replace("East", "E");
        address_to_add = address_to_add.replace("North", "N");
        address_to_add = address_to_add.replace("South", "S");
        address_to_add = address_to_add.replace("West", "W");
        link_api = googleCivic_API + address_to_add;
    }
    public void changeStateToInitial(String s) {
        String[] states = {"Alabama", "Alaska", "Alberta", "American Samoa", "Arizona", "Arkansas",
                "Armed Forces AE", "Armed Forces Americas", "Armed Forces Pacific", "British Columbia",
                "California", "Colorado", "Connecticut", "Delaware", "District Of Columbia", "Florida",
                "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
                "Louisiana", "Maine", "Manitoba", "Maryland", "Massachusetts", "Michigan", "Minnesota",
                "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Brunswick", "New Hampshire",
                "New Jersey", "New Mexico", "New York", "Newfoundland", "North Carolina", "North Dakota",
                "Northwest Territories", "Nova Scotia", "Nunavut", "Ohio", "Oklahoma", "Ontario", "Oregon",
                "Pennsylvania", "Prince Edward Island", "Puerto Rico", "Quebec", "Rhode Island", "Saskatchewan",
                "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virgin Islands",
                "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming", "Yukon Territory"};
        String[] initials = {"AL", "AK", "AB", "AS", "AZ", "AR", "AE", "AA", "AP", "BC", "CA", "CO", "CT",
                "DE", "DC", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MB",
                "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NB", "NH", "NJ", "NM", "NY", "NF",
                "NC", "ND", "NT", "NS", "NU", "OH", "OK", "ON", "OR", "PA", "PE", "PR", "QC", "RI", "SK",
                "SC", "SD", "TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY", "YT"};

        for (int i = 0; i < states.length; i++)
        {
            if (s.equals(states[i]))
            {
                s = s.replace(states[i], initials[i]);
            }
        }
    }

    private boolean checking_for_permission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    }, LOCATION_REQUEST);
            return false;
        }
        return true;
    }

    public void loction_error() {
        topbar.setText(getString(R.string.no_loc));
        error_location = true;
        Toast.makeText(this, getString(R.string.invalid), Toast.LENGTH_LONG).show();
    }

    @SuppressLint("MissingPermissions")
    private void determining_the_location() {
        if (checking_for_permission()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                        if (location != null) {
                            Log.d(TAG, "determineLocation: location :::::: " + location.toString().replace("fused", "gps"));
                            loc = get_place(location);
                            String[] locationResult = loc.split("\n");
                            Log.d(TAG, "determineLocation: Location : " + locationResult[0]);
                            array_officials.clear();
                            URL(locationResult[0]);
                            AADownloder.download(MainActivity.this, link_api);
                        }
                    })
                    .addOnFailureListener(this, e -> Toast.makeText(this,
                            e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    public void loading_recycler() {
        recycler_official = findViewById(R.id.recycler_main);
        AAAdapter officialAdapter = new AAAdapter(array_officials, this);
        recycler_official.setAdapter(officialAdapter);
        recycler_official.setLayoutManager(new LinearLayoutManager(this));
    }

    private boolean internet_connection() {
        ConnectivityManager connectivityManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connectivityManager = getSystemService(ConnectivityManager.class);
        }
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("api", link_api);
        Log.d(TAG, "onSaveInstanceState: " + link_api);
        super.onSaveInstanceState(outState);
    }

    public void dialogue_for_location() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText et1 = new EditText(this);
        et1.setGravity(Gravity.CENTER_HORIZONTAL);
        builder.setMessage(getString(R.string.new_loc_dialog));
        builder.setView(et1);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String ans = et1.getText().toString();

                if (ans.isEmpty() || ans == null) {
                    Toast.makeText(MainActivity.this, R.string.invalid, Toast.LENGTH_SHORT).show();
                } else {
                    array_officials.clear();
                    URL(ans);
                    AADownloder.download(MainActivity.this, link_api);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String get_place(Location loc) {
        StringBuilder sb = new StringBuilder();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            sb.append(String.format(
                    Locale.getDefault(),
                    "%s, %s%n%nProvider: %s%n%n%.5f, %.5f",
                    city, state, loc.getProvider(), loc.getLatitude(), loc.getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options_items, menu);
        return true;
    }

    public void noInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Data cannot be accessed/ loaded without an internet connection.");
        builder.setTitle("No Internet Connection");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_REQUEST) {
            if (permissions.length > 0 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION))
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    determining_the_location();
                } else {
                    Log.d(TAG, "onRequestPermissionsResult: Location permission was denied - cannot determine address");
                }
        }
    }
    @Override
    public void onClick(View view) {
        int index = recycler_official.getChildLayoutPosition(view);
        OfficialsActivity.setCurrentOfficial(array_officials.get(index));
        Intent intent = new Intent(this, OfficialsActivity.class);
        startActivity(intent);
    }
}