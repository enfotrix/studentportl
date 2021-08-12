package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

public class ActivityTimeTable extends AppCompatActivity {

    private RecyclerView rv_timeTable;
    private TextView tv_class, tv_classSection;
    private FirebaseFirestore db;
    private Utils utils;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // hide actionbar and statusBar
        getSupportActionBar().hide();

        // ini views
        IniViews();

        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);


    }

    private void IniViews() {

        rv_timeTable = findViewById(R.id.rv_timeTable);

        tv_class = findViewById(R.id.tv_class);
        tv_classSection = findViewById(R.id.tv_classSection);

    }
}