package com.enfotrix.studentportal.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.enfotrix.studentportal.R;

public class ActivityProgressReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        getSupportActionBar().hide();

    }
}