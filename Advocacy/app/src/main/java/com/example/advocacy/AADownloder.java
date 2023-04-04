package com.example.advocacy;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class AADownloder {
    static MainActivity main_activity;
    private static String main_URL;
    private static final String TAG = "url";
    static RequestQueue queue0;

    //This code has been referred: from lecture and android developers site, I encountered few erros and with help of stackoverflow.

    private static void JSON(String s) {
        try {
            //extracts information from the JSONObject and creates an AAOfficial object with the extracted information.
            // An AAOfficial object represents a government official and stores information like their name, contact information,
            // address, website, and social media handles.
            JSONObject jObjMain = new JSONObject(s);
            JSONObject normalized = jObjMain.getJSONObject("normalizedInput");
            System.out.println("I'm here");


            String address =
                    normalized.getString("line1") + " " + normalized.get("city") + " " + normalized.get("state") + " " + normalized.get("zip");
            JSONArray creator = jObjMain.getJSONArray("offices");
            System.out.println("I'm here");

            JSONArray Arr = jObjMain.getJSONArray("officials");
            //This function takes a JSON string as input and extracts relevant information from it to create instances of AAOfficial class
            // and add it to the official array of the main_activity object.
            System.out.println("I'm here");
            for (int i = 0; i < creator.length(); i++) {

                JSONObject Joff = creator.getJSONObject(i);
                System.out.println("I'm here");
                String j_pos = Joff.getString("name");
                System.out.println("I'm here");
                JSONArray Indices = Joff.getJSONArray("officialIndices");
                System.out.println("I'm here");

                for (int j = 0; j < Indices.length(); j++) {
                    int index = Indices.getInt(j);

                    System.out.println("I'm here");
                    JSONObject J_off = Arr.getJSONObject(index);

                    String j_name = J_off.getString("name");
                    System.out.println("I'm here");
                    String j_party;
                    System.out.println("Part 1");
                    if (J_off.has("party")) {
                        j_party = J_off.getString("party");
                    } else {
                        System.out.println("I'm here");
                        j_party = "Unknown";
                    }
                    System.out.println("I'm here");
                    //contact number
                    //condition
                    //Array
                    System.out.println("Part 2");
                    String contactnumber;
                    System.out.println("I'm here");
                    if (J_off.has("phones")) {
                        System.out.println("I'm here");
                        JSONArray phoneArr = J_off.getJSONArray("phones");
                        contactnumber = phoneArr.getString(0);
                        System.out.println("I'm here");
                    } else {
                        contactnumber = "Unknown";
                    }
                    //Emails
                    //Array
                    //Strings

                    System.out.println("Part4");
                    String j_email;
                    if (J_off.has("emails")) {
                        System.out.println("I'm here");
                        JSONArray p_arr = J_off.getJSONArray("emails");
                        j_email = p_arr.getString(0);
                        System.out.println("I'm here");
                    } else {
                        j_email = "Unknown";
                    }

                    System.out.println("Part 5");
                    String addre;
                    System.out.println("I'm here");
                    if (J_off.has("address")) {
                        System.out.println("I'm here");
                        JSONArray j_addresss = J_off.getJSONArray("address");
                        JSONObject addressOb = j_addresss.getJSONObject(0);
                        System.out.println("I'm here");
                        addre = addressOb.getString("line1") + " " + addressOb.getString("city") + " " + addressOb.getString("state") + " " + addressOb.getString("zip");
                    } else {
                        System.out.println("I'm here");
                        addre = "Unknown";
                    }
                    //The JSON string is parsed using the JSONObject class to obtain the normalized input, which contains information about the address.
                    // The address is created by concatenating the values of "line1", "city", "state" and "zip" fields.
                    System.out.println("Part 5");
                    String j_website;
                    if (J_off.has("urls")) {
                        System.out.println("I'm here");
                        JSONArray urls = J_off.getJSONArray("urls");
                        j_website = urls.getString(0);
                        System.out.println("I'm here");
                    } else {
                        j_website = "Unknown";
                    }

                    System.out.println("I'm here part 6");
                    String pic;

                    System.out.println("Ipat 7e");
                    if (J_off.has("photoUrl")) {
                        System.out.println("I'm here");
                        pic = J_off.getString("photoUrl");
                        System.out.println("I'm here");
                        pic = pic.replace("http", "https");
                    } else {
                        pic = "Unknown";
                    }

                    System.out.println("I'm here media");
                    String[] media_array = new String[3];
                    System.out.println("I'm here");
                    if (J_off.has("channels")) {
                        System.out.println("I'm here");
                        JSONArray j_channels = J_off.getJSONArray("channels");
                        for (int k = 0; k < j_channels.length(); k++) {
                            System.out.println("I'm here");
                            JSONObject media = j_channels.getJSONObject(k);
                            System.out.println("I'm here");
                            String j_type = media.getString("type");
                            if (j_type.equalsIgnoreCase("facebook")) {
                                System.out.println("I'm here");
                                String str = j_type + "," + media.getString("id");
                                media_array[0] = str;
                                System.out.println("I'm here");
                            } else if (j_type.equalsIgnoreCase("twitter")) {
                                System.out.println("I'm here");
                                String str = j_type + "," + media.getString("id");
                                System.out.println("I'm here");
                                media_array[1] = str;
                            } else if (j_type.equalsIgnoreCase("youtube")) {
                                System.out.println("I'm here");
                                String str = j_type + "," + media.getString("id");
                                System.out.println("I'm here");
                                media_array[2] = str;
                            }
                        }
                    } else {
                        System.out.println("I'm here");
                        for (int k = 0; k < 3; k++) {
                            System.out.println("I'm here");
                            media_array[k] = null;
                        }
                    }
                    //It creates an instance of the AAOfficial class using the extracted information and
                    // adds it to the official array of the main_activity object.
                    AAOfficial o = new AAOfficial(j_pos, j_name, j_party,
                            contactnumber, j_email,
                            addre, j_website, pic, media_array);
                    System.out.println("I'm here");
                    main_activity.official_addition_of_array(o);
                }
            }



            //it sets the title of the main_activity object to the normalized address and displays a toast message indicating that the data has been successfully downloaded.
            main_activity.setTitle(address);
            System.out.println("I'm here");
            main_activity.loading_recycler();
            Toast.makeText(main_activity, main_activity.getString(R.string.new_loc), Toast.LENGTH_SHORT).show();
            System.out.println("I'm here");
        }
        //If any exception occurs while parsing the JSON data, it is caught and the stack trace is printed.
        catch (Exception e) {
            e.printStackTrace();
            //any exception occurs while parsing the JSON data, it is caught and the stack trace is printed.
           }
    }

    public static void download(MainActivity m, String url) {
        main_URL = url;
        main_activity = m;
        queue0 = Volley.newRequestQueue(main_activity);
        Log.d(TAG, "downloading Officials: url____________" + main_URL);
        Response.Listener<JSONObject> listener =
                response -> JSON(response.toString());
        Response.ErrorListener error =
                error1 -> main_activity.loction_error();
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, main_URL,
                        null, listener, error);
        System.out.println("I'm here");
        queue0.add(jsonObjectRequest);
    }
}
