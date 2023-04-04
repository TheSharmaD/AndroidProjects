package com.example.androidnotes;
/**
 * Created by SharmaDiksha on 2/13/2023
 * This app allows the creation and maintenance of personal notes.
 */
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {


    private note currNote;
    private List<note> notesList = new ArrayList<>();
    private RecyclerView recycler;
    private Adapter NotesAdapter;
    private static final String TAG = "MainActivity";
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Loading of the JSON File initiated in the onCreate method.
        loadJSONFile();

        String app_name = getResources().getString(R.string.app_name);
        setTitle( app_name + " (" + notesList.size() + ")");
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);

        NotesAdapter = new Adapter(notesList, this);
        recycler.setAdapter(NotesAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this::handleResult);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // loads every source file and build objects on it and returns a view
        getMenuInflater().inflate(R.menu.menu_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.infoPage) {
            // Invoke About Activity
            openAboutActivity();
            return true;
        }
        else if(item.getItemId() == R.id.editPage) {
            // Invoke Add New Note Activity
            openEditActivity();
            return true;
        }
        else {
            Log.d(TAG, "onOptionsItemSelected: Unknown Item: " + item.getTitle());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        index = recycler.getChildAdapterPosition(view);
        currNote = notesList.get(index);
        openEditActivityExist(currNote);
        //Toast.makeText(view.getContext(), "onClick" + note.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onLongClick(View view) {
        int position = recycler.getChildAdapterPosition(view);
        note n = notesList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.deletenote));
        builder.setMessage(getResources().getString(R.string.deletenote)+ " '" + n.getTitle() + "'?");
        builder.setPositiveButton(R.string.yes_btn, (dialog, id) -> {
            notesList.remove(position);
            String app_name = getResources().getString(R.string.app_name);
            setTitle( app_name + " (" + notesList.size() + ")");
            NotesAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Note '" + n.getTitle() + "' Deleted!", Toast.LENGTH_SHORT).show();
            //writeDataToJSON();
        });
        builder.setNegativeButton(R.string.no_btn, (dialog, id) -> {
            //super.onBackPressed();
            return;
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }

    // When + icon is clicked, Edit Activity is opened.
    public void openEditActivity() {
        Intent intent = new Intent(this, EditActivity.class);
        activityResultLauncher.launch(intent);
    }


    // When + icon is clicked, Edit Activity is opened.
    public void openEditActivityExist(note n) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("EDIT_NOTE", n);
        activityResultLauncher.launch(intent);
    }

    // When info icon is clicked, About Activity is opened.
    public void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void handleResult(ActivityResult result) {
        Log.d(TAG, "On handleResult Method: ");
        if(result.getResultCode() == 1) {
            Intent data = result.getData();
            if(data != null) {
                // Extract data here
                currNote = (note) data.getSerializableExtra("NEW_NOTE");
                if(currNote != null) {
                    notesList.add(currNote);
                    Collections.sort(notesList);
                    NotesAdapter.notifyDataSetChanged();
                    String app_name = getResources().getString(R.string.app_name);
                    setTitle( app_name + " (" + notesList.size() + ")");
                    //writeDataToJSON();
                }
            }
            Log.d(TAG, "onActivityResult: Return from Edit Activity: Add note");
        }
        else if(result.getResultCode() == 2) {
            Intent data = result.getData();
            if(data != null) {
                currNote = (note) data.getSerializableExtra("EDIT_NOTE");
                if(currNote != null) {
                    notesList.get(index).setTitle(currNote.getTitle());
                    notesList.get(index).setContent(currNote.getContent());
                    notesList.get(index).setUpdateTime(System.currentTimeMillis());
                    Collections.sort(notesList);
                    NotesAdapter.notifyDataSetChanged();
                }
            }
            Log.d(TAG, "handleResult: Returning from EditActivity: Edited Existing Note!");
        }
        else {
            //Toast.makeText(this, "No Changes Made!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadJSONFile() {
        Log.d(TAG, "loadFile: Loading JSON File");
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String noteTitle = jsonObject.getString("note_title");
                String noteText = jsonObject.getString("note_text");
                long dateMS = jsonObject.getLong("lastUpdateDT");
                note n = new note(noteTitle, noteText);
                n.setUpdateTime(dateMS);
                notesList.add(n);
            }
        }
        catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "readJSONData: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeDataToJSON()  {
        Log.d(TAG, "writeDataToJSON: Saving Notes Data into the JSON File");
        try {
            // Edit strings.xml and remove static filename
            FileOutputStream fos = getApplicationContext().openFileOutput(getResources().getString(R.string.file_name), Context.MODE_PRIVATE);
            JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8));
            jsonWriter.setIndent("  ");
            jsonWriter.beginArray();
            for(note note : notesList) {
                jsonWriter.beginObject();
                jsonWriter.name("note_title").value(note.getTitle());
                jsonWriter.name("note_text").value(note.getContent());
                jsonWriter.name("lastUpdateDT").value(note.getUpdateTime().getTime());
                jsonWriter.endObject();
            }
            jsonWriter.endArray();
            jsonWriter.close();
            Log.d(TAG, "writeDataToJSON: JSON:\n" + notesList.toString());
            //Toast.makeText(this, getResources().getString(R.string.save_note_msg), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        writeDataToJSON();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getResources().getString(R.string.bck_btn_msg), Toast.LENGTH_LONG).show();
        super.onBackPressed();
    }
}
