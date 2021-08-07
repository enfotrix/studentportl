package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Adapters.Adapter_DateSheet;
import com.enfotrix.studentportal.R;

public class Activity_DateSheet extends AppCompatActivity {

    private TextView tv_class, tv_classSection;

    private RecyclerView rv_dateSheet;
    private Adapter_DateSheet adapterDateSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_sheet);

        getSupportActionBar().hide();

        // ini views
        IniViews();

    }

    private void IniViews() {

        tv_class = findViewById(R.id.tv_class);
        tv_classSection = findViewById(R.id.tv_classSection);

        rv_dateSheet = findViewById(R.id.rv_dateSheet);

    }
}