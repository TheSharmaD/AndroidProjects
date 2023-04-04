package com.example.androidnotes;
/**
 * Created by SharmaDiksha on 2/13/2023
 */
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
public class NoteViewHolder extends RecyclerView.ViewHolder {
    public TextView notesTitle;
    public TextView notesUpdateTime;
    public TextView notesContent;

    public NoteViewHolder(View view) {
        super(view);
        notesTitle = (TextView) view.findViewById(R.id.n_Title);
        notesUpdateTime = (TextView) view.findViewById(R.id.n_UpdateTime);
        notesContent = (TextView) view.findViewById(R.id.n_Content);
    }
}