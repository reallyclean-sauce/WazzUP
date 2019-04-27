package com.eeepips.wazzup.AnnouncementActivities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eeepips.wazzup.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final int ADD_EVENT_ACTIVITY = 1;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextVenue;
    private String editButtonDate;
    private String editButtonTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextVenue = findViewById(R.id.edit_text_venue);

        Button button_date = (Button) findViewById(R.id.edit_button_date);
        button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");

            }
        });

        Button button_time = (Button) findViewById(R.id.edit_button_time);
        button_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Request Event");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_EVENT_ACTIVITY) {
            Toast.makeText(this, "Request Sent", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void requestEvent() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText(). toString();
        String venue = editTextDescription.getText().toString();
        String date = editButtonDate;
        String time = editButtonTime;

        if (title.trim().isEmpty() || description.trim().isEmpty() || venue.trim().isEmpty() || date.trim().isEmpty() || time.trim().isEmpty()) {
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent(AddEventActivity.this, ExcelUpload.class);
        data.putExtra(ExcelUpload.EXTRA_TITLE_ADD, title);
        data.putExtra(ExcelUpload.EXTRA_DESCRIPTION_ADD, description);
        data.putExtra(ExcelUpload.EXTRA_VENUE_ADD, venue);
        data.putExtra(ExcelUpload.EXTRA_DATE_ADD, date);
        data.putExtra(ExcelUpload.EXTRA_TIME_ADD, time);

        startActivityForResult(data, ADD_EVENT_ACTIVITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.request_event_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.request_event:
                requestEvent();
                return super.onOptionsItemSelected(item);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.textView_date);
        textView.setText(currentDateString);

        editButtonDate = currentDateString;

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.textView_time);
        textView.setText(hourOfDay + ":" + minute);

        editButtonTime = hourOfDay + ":" + minute;
    }


}
