package com.example.advocacy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.ObjectInputStream;
/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class OfficialsActivity extends AppCompatActivity {
    static TextView topbar_offical;
    TextView name_official,address_official,email_official,website_official,contact_official;
    ImageView party_official,image_official;
    private Picasso picasso;
    private boolean picture_availability = false;
    Button fb_btn,twttr_btn,yt_btn;
    TextView position_official,party_of_official;
    static AAOfficial cofficial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officials);
        //Initialization
        party_official = findViewById(R.id.logo1_official);
        name_official = findViewById(R.id.name_official1);
        address_official = findViewById(R.id.address_official);
        image_official = findViewById(R.id.photo_official);
        topbar_offical = findViewById(R.id.topbar_official);
        email_official = findViewById(R.id.email_official);
        fb_btn = findViewById(R.id.facebookbtn_official);
        twttr_btn = findViewById(R.id.twitterbtn_official);
        contact_official = findViewById(R.id.contact_official);
        party_of_official = findViewById(R.id.party_official);
        website_official = findViewById(R.id.website_official);
        yt_btn = findViewById(R.id.youtubebtn_official);
        position_official = findViewById(R.id.position_official);
        //Picasso set.get
        picasso = Picasso.get();
        setTitle(MainActivity.getAddress());

        //condition
        if(cofficial != null){
            View root = party_of_official.getRootView();
            if (cofficial.getPart_official().equalsIgnoreCase("(Republican Party)"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    root.setBackgroundColor(getResources().getColor(R.color.red, this.getTheme()));
                }
                party_official.setImageResource(R.drawable.rep_logo);
            }
            else if( cofficial.getPart_official().equalsIgnoreCase("(Democratic Party)") )
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    root.setBackgroundColor(getResources().getColor(R.color.blue, this.getTheme()));
                }
                party_official.setImageResource(R.drawable.dem_logo);
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    root.setBackgroundColor(getResources().getColor(R.color.black, this.getTheme()));
                }
                party_official.setVisibility(View.GONE);
            }
            loadOfficial();
        }
    }
    //load the data official
    public void loadOfficial(){
        party_of_official.setText(cofficial.getPart_official());

        position_official.setText(cofficial.getPosition_official());
        name_official.setText(cofficial.getName_official());

        //////////////////////////////////////////////////////////////////////////

        if(cofficial.getPhotoURL_official().equalsIgnoreCase("Unknown")){
            picture_availability = true;
        }
        if(cofficial.getEmail_official().equals("Unknown")){
            email_official.setVisibility(View.GONE);
        }
        else{
            email_official.setText(new StringBuilder().append(getString(R.string.email)).append(" ")
                    .append(cofficial.getEmail_official()).toString());
            Linkify.addLinks(email_official, Linkify.ALL);
            email_official.setLinkTextColor(getResources().getColor(R.color.white));
        }

        if (cofficial.getSocialLink()[0] == null){
            fb_btn.setVisibility(View.GONE);
        }
    ///Error Debug////
        //todo
        if(cofficial.getWebsite_official().equals("Unknown")){
            website_official.setVisibility(View.GONE);
        }
        else{
            website_official.setText(new StringBuilder().append(getString(R.string.website)).append(" ")
                    .append(cofficial.getWebsite_official()).toString());
            Linkify.addLinks(website_official, Linkify.ALL);
            website_official.setLinkTextColor(getResources().getColor(R.color.white));
        }
        if(cofficial.getSocialLink()[1] == null){
            twttr_btn.setVisibility(View.GONE);
        }
        if(cofficial.getAddress_official().equals("Unknown")){
            address_official.setVisibility(View.GONE);
        }
        else{
            SpannableString officialAddr = new SpannableString( getString(R.string.address) + " " +
                    cofficial.getAddress_official());
            officialAddr.setSpan(new UnderlineSpan(), 0,officialAddr.length(), 0);
            officialAddr.setSpan(new ForegroundColorSpan(0xFFFFFFFF), 0, officialAddr.length(), 0);
            address_official.setText(officialAddr);
            address_official.setClickable(true);
        }
        if(cofficial.getSocialLink()[2] == null){
            yt_btn.setVisibility(View.GONE);
        }
        if(cofficial.getContact_official().equals("Unknown")){
            contact_official.setVisibility(View.GONE);
        }else{
            contact_official.setText(new StringBuilder().append(getString(R.string.phone)).append(" ")
                    .append(cofficial.getContact_official()).toString());
            Linkify.addLinks(contact_official, Linkify.ALL);
            contact_official.setLinkTextColor(getResources().getColor(R.color.white));
        }
        picasso.load(cofficial.getPhotoURL_official())
                .error(R.drawable.missing)
                .placeholder(R.drawable.placeholder)
                .into(image_official);
    }
    public static String getAddress(){
        return topbar_offical.getText().toString();
    }
    @Override
    public void onBackPressed(){
        cofficial = null;
        finish();
    }
    public void setTitle(String s){
        topbar_offical.setText(s);
    }

    //Button Functions
    public void onClick_photo(View view) {
        if(!picture_availability){//tis will check if the picture is available or not
            PhotoActivity.setCurrent(cofficial);
            Intent intent = new Intent(this, PhotoActivity.class);
            startActivity(intent);
        }
    }
    public static void setCurrentOfficial(AAOfficial o){
        cofficial = o; //this will set a value for the present official
    }
    public void onClickParty(View view){
        String url;
        Intent intent = new Intent();

        if( cofficial.getPart_official().equalsIgnoreCase("(Democratic Party)") ){
            url = "https://democrats.org";
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
        }
        else {
            url = "https://www.gop.com";
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
        }
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void OnClickFB(View v) {
        String FACEBOOK_URL = "https://www.facebook.com/" + cofficial.getSocialLink()[0].
                substring( cofficial.getSocialLink()[0].indexOf(",")+1 );;
        String urlToUse;
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0)
                    .versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                urlToUse = "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                ObjectInputStream.GetField channels = null;
                urlToUse = "fb://page/" + channels.get("Facebook", 0);
            }
        } catch (PackageManager.NameNotFoundException | IOException e) {
            urlToUse = FACEBOOK_URL; //normal web url
        }
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        facebookIntent.setData(Uri.parse(urlToUse));
        startActivity(facebookIntent);
    }
    public void onClickUTube(View v) {
        String name = cofficial.getSocialLink()[2].substring( cofficial.getSocialLink()[2].indexOf(",")+1 );
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/" + name));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/" + name)));
        }
    }
    public void onClickTwit(View v) {
        Intent intent = null;
        String name = cofficial.getSocialLink()[1].substring( cofficial.getSocialLink()[1].indexOf(",")+1 );
        try {
            getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
        }
        startActivity(intent);
    }
    public void onClickAddress(View view){
        String geo = "geo:0,0?q=";
        String address = cofficial.getAddress_official().replace(" ", "+");
        Intent searchAddress = new Intent(Intent.ACTION_VIEW, Uri.parse(geo+address));
        startActivity(searchAddress);
    }
}