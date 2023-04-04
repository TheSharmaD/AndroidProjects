package com.example.androidnotes;
/**
 * Created by SharmaDiksha on 2/13/2023
 */
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class Adapter  extends RecyclerView.Adapter<NoteViewHolder>  {
    private static final String TAG = "Adapter";
    private List<note> noteList;
    private MainActivity mainActivity;
    public Adapter(List<note> noteList, MainActivity mainActivity){
        this.noteList = noteList;
        this.mainActivity = mainActivity;
    }

    /**
     * The onCreateViewHolder method is used to create a new ViewHolder object, which is responsible for displaying a single item in a RecyclerView.
     * The method takes in a ViewGroup object parent and an integer viewType as arguments.
     */
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateNoteViewHolder: MAKING NEW NoteViewHolder");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new NoteViewHolder(itemView);
    }

    /**
     * NoteViewHolder and a position, and it retrieves the corresponding Note object from a list of notes (noteList) at the specified position.
     * The method then sets the title, content, and update time of the Note on the ViewHolder's views.
     */
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Filling ViewHolder Notes: " + position);

        note n = noteList.get(position);
        // Substring - NoteTitle limit to 80 Characters on MainActivity Display list
        String TitleFullLen = n.getTitle();
        if(TitleFullLen.length() > 80) {
            TitleFullLen = TitleFullLen.substring(0, 80);
            TitleFullLen = TitleFullLen + "...";
        }
        holder.notesTitle.setText(TitleFullLen);
        // Substring - NoteText limit to 80 Characters on MainActivity Display list
        String ContentFullLen = n.getContent();
        if(ContentFullLen.length() > 80) {
            ContentFullLen = ContentFullLen.substring(0, 80);
            ContentFullLen = ContentFullLen + "...";
        }
        holder.notesContent.setText(ContentFullLen);
        holder.notesUpdateTime.setText(n.getUpdateTime().toString());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
}

