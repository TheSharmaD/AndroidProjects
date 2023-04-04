package com.example.advocacy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class AboutActivity extends AppCompatActivity {
    TextView ApiLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        // set the layout for the activity
        ApiLink = findViewById(R.id.link_about);
        // find the TextView in the layout using its ID
    }

    //onClickAPI_about() method, a new intent is created using the Intent.
    // ACTION_VIEW action, which is used to view the data (in this case, the API link) by another activity.
    public void onClickAPI_about(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // create a new intent with ACTION_VIEW action
        String url = getString(R.string.api);
        // get the string resource named "api"
        intent.setAction(Intent.ACTION_VIEW);
        // this will add an action to the intent
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        // this will add a category to the intent
        intent.setData(Uri.parse(url));
        // set the data URI for the intent to the URL
        startActivity(intent);
        // start the activity with the intent
    }
}
