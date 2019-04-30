package com.eeepips.wazzup.AnnouncementActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.eeepips.wazzup.R;

//import com.eeepips.wazzup.R;

public class ViewEventActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.eeepips.announcementpage.EXTRA_ID";
    public static final String EXTRA_PRIORITY =
            "com.eeepips.announcementpage.EXTRA_PRIORITY";
    public static final String EXTRA_TITLE =
            "com.eeepips.announcementpage.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.eeepips.announcementpage.EXTRA_DESCRIPTION";
    public static final String EXTRA_VENUE =
            "com.eeepips.announcementpage.EXTRA_VENUE";
    public static final String EXTRA_DATE =
            "com.eeepips.announcementpage.EXTRA_DATE";
    public static final String EXTRA_TIME =
            "com.eeepips.announcementpage.EXTRA_TIME";

    private TextView textViewTitle;
    private TextView textViewDescription;
    private TextView textViewVenue;
    private TextView textViewDate;
    private TextView textViewTime;
//    private TextView textViewPriority;

    //    Not sure if finished
//    But should be finished
//    Since all that is needed in here is to
//    View the events, and be able to go back to menu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        textViewTitle = findViewById(R.id.event_view_title);
        textViewDescription = findViewById(R.id.event_view_description);
        textViewVenue = findViewById(R.id.event_view_venue);
        textViewDate = findViewById(R.id.event_view_date);
        textViewTime = findViewById(R.id.event_view_time);
//        textViewPriority = findViewById(R.id.event_view_priority);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("View Event");
            textViewTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            textViewDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            textViewVenue.setText("Venue: " +intent.getStringExtra(EXTRA_VENUE));
            textViewDate.setText("Date: " + intent.getStringExtra(EXTRA_DATE));
            textViewTime.setText("Time: " + intent.getStringExtra(EXTRA_TIME));
//            textViewPriority.setText(intent.getStringExtra(EXTRA_PRIORITY));

        } else {
            setTitle("Add Event");
        }

    }

    //    Wait for Google Maps API
    private void viewVenue() {
        // Go back here and try to integrate google maps API
//        String title = textViewTitle.getText().toString();
//        String description = textViewDescription
//        setResult(RESULT_OK, )
//        String title = editTextTitle.getText().toString();
//        String description = editTextDescription.getText().toString();
//        String venue = editTextVenue.getText().toString();
//        String date = editTextDate.getText().toString();
//        String time = editTextTime.getText().toString();
//        int priority = numberPickerPriority.getValue();
//
//        if (title.trim().isEmpty() || description.trim().isEmpty() || venue.trim().isEmpty() ||
//                date.trim().isEmpty() || time.trim().isEmpty()) {
//            Toast.makeText(this, "Please edit all required fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
        Intent data = new Intent();

        int id = getIntent().getIntExtra(EXTRA_ID, 1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_DESCRIPTION, description);
//        data.putExtra(EXTRA_VENUE, venue);
//        data.putExtra(EXTRA_DATE, date);
//        data.putExtra(EXTRA_TIME, time);
//        data.putExtra(EXTRA_PRIORITY, priority);
        setResult(RESULT_OK, data);
        finish();
    }

    //    Wait forerride
    ////    public boolean onOptionsItemSelected(MenuItem item) {
    ////        switch (item.getItemId()) {
    ////            case R.id.view_venue:
    ////                viewVenue();
    ////                return true;
    ////            default:
    ////                return super.onOptionsItemSelected(item);
    ////        }
    ////
    ////    } Google Maps API
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.view_venue_menu, menu);
//        return true;
//    }


    //    Wait for Google Maps API
//    @Ov
}
