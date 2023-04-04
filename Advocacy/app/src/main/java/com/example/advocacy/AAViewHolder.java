package com.example.advocacy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by SharmaDiksha on 3/28/2023 2023
 */
public class AAViewHolder extends RecyclerView.ViewHolder {
    //three public member variables to hold the references to the views inside the layout:
    public ImageView photo_official;
    public TextView name_official;
    public TextView title_official;

    //The constructor of the custom ViewHolder class takes a single parameter of type View.
    // This view represents the root view of the item layout.
    // Inside the constructor, the code initializes the member variables by finding the views by their IDs using the findViewById() method:
    public AAViewHolder(@NonNull View view) {
        super(view);
        title_official = view.findViewById(R.id.title_entry);
        name_official = view.findViewById(R.id.name_entry);
        photo_official = view.findViewById(R.id.photo_entry);
    }
}
