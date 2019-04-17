package com.eeepips.wazzup;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.eeepips.wazzup.AnnouncementActivities.ExcelUpdate;
import com.eeepips.wazzup.AnnouncementActivities.ViewEventActivity;
import com.eeepips.wazzup.Database.Event;
import com.eeepips.wazzup.Database.EventAdapter;
import com.eeepips.wazzup.Database.EventViewModel;

import java.util.ArrayList;
import java.util.List;


public class AnnouncementPage extends AppCompatActivity{
    //    public static final int VIEW_EVENT_REQUEST = 1;
    public static final int ADD_EVENT_REQUEST = 1;
    public static final int VIEW_EVENT_REQUEST = 2;
    public static final int REFRESH_EVENTS_REQUEST = 3;

    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_page);


        // For Refreshing all the events
        // And getting the new events
        // provided by the Spreadsheet API
//        FloatingActionButton buttonRefreshEvents = findViewById(R.id.button_refresh);
//        buttonRefreshEvents.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Paste here the delete all button
////                Intent intent = new Intent(MainActivity.this, );
//            }
//        });


        // This adds the the floating action button
        // Upon clicking, it goes to a new activity AddEventActivity
        // which allows to add an event data to the database
        FloatingActionButton buttonAddEvent = findViewById(R.id.button_refresh);
        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnouncementPage.this, ExcelUpdate.class);
                startActivityForResult(intent, REFRESH_EVENTS_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EventAdapter adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(@Nullable List<Event> events) {
                // Update Recycle Viewer
                adapter.setEvents(events);
            }
        });

        // Allows the elements to be swipe-able
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                // Gets which element is being swiped
//                // And deletes it
//                eventViewModel.delete(adapter.getEventAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(AnnouncementPage.this, "Note deleted!", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(recyclerView);

        // Called when any item is clicked
        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                Intent intent = new Intent(AnnouncementPage.this, ViewEventActivity.class);
                intent.putExtra(ViewEventActivity.EXTRA_ID, event.getId());
                intent.putExtra(ViewEventActivity.EXTRA_TITLE, event.getTitle());
                intent.putExtra(ViewEventActivity.EXTRA_DESCRIPTION, event.getDescription());
                intent.putExtra(ViewEventActivity.EXTRA_VENUE, event.getVenue());
                intent.putExtra(ViewEventActivity.EXTRA_DATE, event.getDate());
                intent.putExtra(ViewEventActivity.EXTRA_TIME, event.getTime());
                intent.putExtra(ViewEventActivity.EXTRA_PRIORITY, event.getPriority());

                startActivityForResult(intent, VIEW_EVENT_REQUEST);
            }
        });

    }

    // This is done by after any activity is finished (finish();)
    // Processes: if adding event
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VIEW_EVENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(ViewEventActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Event can't be viewed.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Event Viewed!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == REFRESH_EVENTS_REQUEST && resultCode == RESULT_OK) {
            ArrayList<String> resource = data.getStringArrayListExtra("output");
            String event_string;
            String[] tokens;
            String delims = "[,]+";


            String title;
            String description;
            String venue;
            String date;
            String time;
            int priority;

            Event event;

//            event_string = resource.get(0);
//            tokens = event_string.split(delims);
//            title = tokens[0];
//            description = tokens[1];
//            venue = tokens[2];
//            date = tokens[3];
//            time = tokens[4];
//            priority = Integer.parseInt(tokens[5]);
//
//            event = new Event(title, description, venue, date, time, priority);
//            eventViewModel.insert(event);
            // Delete All events first before adding
            eventViewModel.deleteAllEvents();

            for (int i = 0; i < resource.size(); i++) {
                event_string = resource.get(i);
                tokens = event_string.split(delims);
                title = tokens[0];
                description = tokens[1];
                venue = tokens[2];
                date = tokens[3];
                time = tokens[4];
                priority = Integer.parseInt(tokens[5]);

                event = new Event(title, description, venue, date, time, priority);
                eventViewModel.insert(event);
            }

            Toast.makeText(this, "Events has been updated.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Event Not Saved", Toast.LENGTH_SHORT).show();
        }

    }


    // Inflates the menu button
    // then shows whats inside that menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    // Parses what happens on the Options selected
    // One option deletes all Events
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_events:
                eventViewModel.deleteAllEvents();
                Toast.makeText(this, "All events deleted.", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // This is for saving the note
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == VIEW_EVENT_REQUEST) {
//            String title =
//        }
//    }
}
