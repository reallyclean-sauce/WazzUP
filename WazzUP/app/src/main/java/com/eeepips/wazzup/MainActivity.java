package com.eeepips.wazzup;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.eeepips.wazzup.AnnouncementActivities.ExcelUpdate;
import com.eeepips.wazzup.AnnouncementActivities.VenueEventsActivity;
import com.eeepips.wazzup.AnnouncementActivities.ViewEventActivity;
import com.eeepips.wazzup.Database.Event;
import com.eeepips.wazzup.Database.EventAdapter;
import com.eeepips.wazzup.Database.EventViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //    public static final int VIEW_EVENT_REQUEST = 1;
    public static final int ADD_EVENT_REQUEST = 1;
    public static final int VIEW_EVENT_REQUEST = 2;
    public static final int REFRESH_EVENTS_REQUEST = 3;

    public static EventViewModel eventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent = new Intent(MainActivity.this, ExcelUpdate.class);
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
                Intent intent = new Intent(MainActivity.this, ViewEventActivity.class);
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
//            Toast.makeText(this, "Event Not Saved", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_events) {
            // Start AnnouncementPage
//            Intent intent = new Intent(MainActivity.this, AnnouncementPage.class);
//            startActivity(intent);
        } else if (id == R.id.nav_map) {
            Intent intent = new Intent(MainActivity.this, MapView.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
