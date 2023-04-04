package com.example.advocacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class PhotoActivity extends AppCompatActivity {
    //Variables
    TextView topbar_photo,pos_photo,name_photo;
    ImageView photo_photo,img_photo;
    private Picasso picasso;
    static AAOfficial current;
    public static void setCurrent( AAOfficial o ){
        current = o;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        //initialization
        img_photo = findViewById(R.id.party_photo);
        topbar_photo = findViewById(R.id.topbar_photo);
        photo_photo = findViewById(R.id.photo_photo);
        pos_photo = findViewById(R.id.position_photo);
        name_photo = findViewById(R.id.name_photo);
        //Picasso
        picasso = Picasso.get();
        //set topbar
        topbar_photo.setText( OfficialsActivity.getAddress() );
        //set position
        pos_photo.setText(current.getPosition_official());
        //set name of the official
        name_photo.setText(current.getName_official());
        View root = pos_photo.getRootView();
        //condition
        if( current.getPart_official().equalsIgnoreCase("(Democratic Party)") ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                root.setBackgroundColor(getResources().getColor(R.color.blue, this.getTheme()));
            }
            img_photo.setImageResource(R.drawable.dem_logo);
        }
        else if (current.getPart_official().equalsIgnoreCase("(Republican Party)")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                root.setBackgroundColor(getResources().getColor(R.color.red, this.getTheme()));
            }
            img_photo.setImageResource(R.drawable.rep_logo);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                root.setBackgroundColor(getResources().getColor(R.color.black, this.getTheme()));
            }
            img_photo.setVisibility(View.GONE);
        }
        //picasso load
        picasso.load(current.getPhotoURL_official())
                .error(R.drawable.brokenimage)
                .placeholder(R.drawable.placeholder)
                .into(photo_photo);
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}