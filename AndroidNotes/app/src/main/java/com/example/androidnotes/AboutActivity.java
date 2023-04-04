package com.example.androidnotes;
/**
 * Created by SharmaDiksha on 2/13/2023
 */
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/**
 * The About Activity should contain a full-screen image background (use any non-copywritten image you desire).
 * Over the background image, key information on the application should be clearly displayed (clearly readable).
 * This information should include the application title, a copyright date and your name, and the version number
 * There is no functionality present on this activity.
 * The only action a user can take is to press the Back arrow to exit the activity
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }
}