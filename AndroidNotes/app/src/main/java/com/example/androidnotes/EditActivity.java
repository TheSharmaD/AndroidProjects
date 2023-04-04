package com.example.androidnotes;
/**
 * Created by SharmaDiksha on 2/13/2023
 */
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class EditActivity extends AppCompatActivity {
    //Editable Fields
    private EditText note_Title;
    //The note title is a single-line text field (EditText) where the user can enter or edit a title for the current note (no size limit).
    private EditText note_Content;
    //The note text is a multi-line text area (EditText) with no size limit.
    // This should have scrolling capability for when notes exceed the size of the activity
    private note curr_Note;
    private static final String TAG = "EditActivity";
    private boolean NewNote = false;
    private long tempTime;
    private String oldTitle;
    private String oldContent;

    //On Create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        //ID's
        note_Title =  findViewById(R.id.ed_Title);
        note_Content = findViewById(R.id.ed_Content);
        curr_Note = new note(null, null);

        Intent intent = getIntent();
        if(intent.hasExtra("EDIT_NOTE")) {
            curr_Note = (note) intent.getSerializableExtra("EDIT_NOTE");
            if(curr_Note != null) {
                oldTitle = curr_Note.getTitle();
                oldContent = curr_Note.getContent();
                tempTime = intent.getLongExtra("Time", 0);
                note_Title.setText(oldTitle);
                note_Content.setText(oldContent);
            }
            else {
                note_Title.setText(R.string.n_not_found);
            }
            NewNote = false;
        }
        else {
            NewNote = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // loads every source file and build objects on it and returns a view
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Btn_Save) {
            saveEditActivity();
            return true;
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: Unknown Item" + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }
    // When + icon is clicked, Edit Activity is opened.
    public void saveEditActivity() {

        String noteTitle = note_Title.getText().toString();
        String noteText = note_Content.getText().toString();

        // Add New Note
        if(NewNote) {
            if(noteTitle.trim().isEmpty()) {
                NoTitleDialogBox();
                return;
            }
            note newNote = new note(noteTitle, noteText);
            Intent intent = new Intent();
            intent.putExtra("NEW_NOTE", newNote);
            setResult(1, intent);
            finish();
        } // Edit Existing Note
        else {
            if(noteTitle.trim().isEmpty()) {
                NoTitleDialogBox();
                return;
            }
            curr_Note.setTitle(noteTitle);
            curr_Note.setContent(noteText);
            curr_Note.setUpdateTime(tempTime);
            Intent intent = new Intent();
            intent.putExtra("EDIT_NOTE", curr_Note);
            setResult(2, intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        String noteTitle = note_Title.getText().toString();
        String noteText = note_Content.getText().toString();

        if(NewNote && (!noteTitle.trim().isEmpty())) {
            SaveDialogBox();
        }
        else if(NewNote && (!noteText.trim().isEmpty())) {
            SaveDialogBox();
        }
        else if(!(curr_Note == null) && !(noteTitle.equals(curr_Note.getTitle()))) {
            SaveDialogBox();
        }
        else if(!(curr_Note == null) && !(noteText.equals(curr_Note.getContent()))) {
            SaveDialogBox();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.no_changes), Toast.LENGTH_SHORT).show();
            EditActivity.super.onBackPressed();
            return;
        }
    }
    public void NoTitleDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_title_head);
        builder.setMessage(R.string.no_title_warning);
        builder.setPositiveButton(R.string.yes_btn, (dialogInterface, i) -> EditActivity.super.onBackPressed());
        builder.setNegativeButton(R.string.no_btn, (dialogInterface, i) -> {
            return;
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void SaveDialogBox() {
        String noteTitle = note_Title.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.save_n_title);
        builder.setMessage(getString(R.string.save_n_dialog) + " '" + noteTitle + "' ?");
        builder.setPositiveButton(R.string.yes_btn, (dialogInterface, i) -> saveEditActivity());
        builder.setNegativeButton(R.string.no_btn, (dialogInterface, i) -> EditActivity.super.onBackPressed());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String getCurrTime() {
        DateFormat df = new SimpleDateFormat("E MMM d, h:mm a");
        return df.format(new Date()).toString();
    }
}
