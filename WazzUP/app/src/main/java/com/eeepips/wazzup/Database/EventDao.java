package com.eeepips.wazzup.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//import androidx.room.Update;

@Dao
public interface EventDao {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table")
    void deleteAllEvents();


    //LiveData<List<Event>> getAllEvents();
    @Query("SELECT * FROM event_table ORDER BY priority ASC")
    LiveData<List<Event>> getAllEvents();

    @Query("SELECT * FROM event_table WHERE venue LIKE :search_venue ORDER BY priority ASC")
    LiveData<List<Event>> getVenueEvents(String search_venue);
}
