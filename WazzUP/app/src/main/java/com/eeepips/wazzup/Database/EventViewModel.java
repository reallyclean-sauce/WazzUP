package com.eeepips.wazzup.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;

public class EventViewModel extends AndroidViewModel {

    private EventRepository repository;
    //    private LiveData<List<Event>> allEvents
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Event>> venueEvents;
    private LiveData<List<Event>> dateEvents;


    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new EventRepository(application);
        allEvents = repository.getAllEvents();
    }

    public void insert(Event event) {
        repository.insert(event);
    }

    public void update(Event event) {
        repository.update(event);
    }

    public void delete(Event event) {
        repository.delete(event);
    }

    public void deleteAllEvents() {
        repository.deleteAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public LiveData<List<Event>> getVenueEvents(String search_venue) {
        venueEvents = repository.getVenueEvents(search_venue);
        return venueEvents;
    }

    public LiveData<List<Event>> getDateEvents(String search_date) {
        dateEvents = repository.getDateEvents(search_date);
        return dateEvents;
    }
}
