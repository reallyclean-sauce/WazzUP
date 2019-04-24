package com.eeepips.wazzup.AnnouncementActivities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.eeepips.wazzup.AnnouncementPage;
import com.eeepips.wazzup.Database.Event;
import com.eeepips.wazzup.Database.EventAdapter;
import com.eeepips.wazzup.Database.EventViewModel;
import com.eeepips.wazzup.R;

import java.util.List;

public class DateEventsActivity extends AppCompatActivity {
    public static final int VIEW_EVENT_REQUEST = 2;
    public static String search_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_events);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("Venue");

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

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EventAdapter adapter = new EventAdapter();
        recyclerView.setAdapter(adapter);

        AnnouncementPage.eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        AnnouncementPage.eventViewModel.getDateEvents(search_date).observe(this, new Observer<List<Event>>() {
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
                Intent intent = new Intent(DateEventsActivity.this, ViewEventActivity.class);
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

//            Toast.makeText(this, "Event Viewed!", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Event Not Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
