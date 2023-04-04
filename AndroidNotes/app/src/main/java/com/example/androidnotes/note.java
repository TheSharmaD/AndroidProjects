package com.example.androidnotes;
/**
 * Created by SharmaDiksha on 2/13/2023
 */
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Date;
public class note implements Serializable, Comparable<note> {

    private String noteTitle = "";
    private String noteContent = "";
    private Date lastUpdateTime;
    //private static int counter = 1;

    public note(String noteTitle, String noteText) {
        this.noteTitle = noteTitle;
        this.noteContent = noteText;
        this.lastUpdateTime = new Date();
    }

    public String getTitle() {
        return noteTitle;
    }

    public String getContent() {
        return noteContent;
    }

    public Date getUpdateTime() {
        return lastUpdateTime;
    }

    public void setTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public void setContent(String noteText) {
        this.noteContent = noteText;
    }

    public void setUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = new Date(lastUpdateTime);
    }



    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", lastSavedDate=" + lastUpdateTime +
                '}';
    }

    @Override
    public int compareTo(note n) {
        if(lastUpdateTime.before(n.lastUpdateTime)) {
            return 1;
        }
        else if(lastUpdateTime.after(n.lastUpdateTime)) {
            return -1;
        }
        return 0;
    }
    }
