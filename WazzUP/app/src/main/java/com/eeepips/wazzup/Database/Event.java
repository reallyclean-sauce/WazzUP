package com.eeepips.wazzup.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

//import androidx.room.Entity;
//import androidx.room.PrimaryKey;

@Entity(tableName = "event_table")
public class Event {
    // Column names
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    private String venue;

    private String date;

    private String time;

    private int priority;

    // Constructor
    public Event(String title, String description, String venue, String date, String time, int priority) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPriority() {
        return priority;
    }
}
