package com.enfotrix.studentportal.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.enfotrix.studentportal.R;

public class ActivityTimeTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // hide actionbar and statusBar
        getSupportActionBar().hide();

    }
}