package com.project.srmcalender;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class Activity2cal extends AppCompatActivity {

    private CalendarView cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2cal);

        cal = findViewById(R.id.thecal);

        Intent intent = getIntent();
        final String val = intent.getStringExtra("val");

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView arg0, int year, int month,
                                            int date) {
                Intent i = new Intent(Activity2cal.this, EventPage.class);
                i.putExtra("thedate", date+ "/"+month+"/"+year);
                i.putExtra("val", val);
                startActivity(i);
            }
        });
    }
}
