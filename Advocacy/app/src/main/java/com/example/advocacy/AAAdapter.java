package com.example.advocacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class AAAdapter extends RecyclerView.Adapter<AAViewHolder> {
    private ArrayList<AAOfficial> array_of_officials = new ArrayList<>();
    //An array list of "AAOfficial" objects is defined as "array_of_officials"
    // and initialized to an empty array.

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    private final MainActivity main_activity;
    private Picasso picasso;
        // "array_of_officials" is initialized with the ArrayList of
    // AAOfficial objects passed as a parameter, and the Picasso library is used to get an instance of it.
    @Override
    public void onBindViewHolder(@NonNull AAViewHolder holder, int position) {
        //The "onBindViewHolder" method is overridden
        // and called when each item in the RecyclerView needs to be populated.
        System.out.println("Im here");
        AAOfficial o = array_of_officials.get(position);
        // It gets the "AAOfficial" object at the current position from the "array_of_officials" list and sets the title and
        holder.title_official.setText(o.getPosition_official());
        String temp = o.getName_official() + " " + o.getPart_official();
        System.out.println("Debuggerrr");
        holder.name_official.setText(temp);
        holder.name_official.setText(temp);
        //name of the official in the corresponding TextViews of the ViewHolder. Additionally,
        // the Picasso library is used to load the photo of the official from the provided URL,
        // with error and placeholder images being shown in case of failure or slow loading.
        picasso.load(o.getPhotoURL_official()).error(R.drawable.missing).placeholder(R.drawable.placeholder).into(holder.photo_official);
    }
    //The "onCreateViewHolder" method is overridden to inflate the layout for each item in the RecyclerView from the "entry.xml" file
    // using the LayoutInflater. The "itemView" is created by inflating the "entry" layout with the parent ViewGroup as the container
    // and "false" as the boolean value for whether the inflated view should be attached to the root ViewGroup.
    @NonNull
    @Override
    public AAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.entry, parent, false);
        //An OnClickListener is added to the "itemView" to detect when it is clicked and passes the event to the MainActivity instance.
        itemView.setOnClickListener(main_activity);
        System.out.println("Im here at AAViewholder");
        return new AAViewHolder(itemView);
    }
    public AAAdapter(ArrayList<AAOfficial> o, MainActivity m) {
        this.array_of_officials = o;
        this.main_activity = m;
        System.out.println("Debugger at AADapter");
        picasso = Picasso.get();
    }

    @Override
    public int getItemCount() {
        return array_of_officials.size();
    }
}
