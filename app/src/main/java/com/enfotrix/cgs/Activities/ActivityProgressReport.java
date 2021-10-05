package com.enfotrix.cgs.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.enfotrix.cgs.R;

public class ActivityProgressReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report);

        getSupportActionBar().hide();

    }
}