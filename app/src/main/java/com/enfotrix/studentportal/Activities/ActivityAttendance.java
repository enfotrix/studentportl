package com.enfotrix.studentportal.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Adapters.Adapter_Month;
import com.enfotrix.studentportal.Models.Model_Month;
import com.enfotrix.studentportal.R;

import java.util.ArrayList;

public class ActivityAttendance extends AppCompatActivity {

    // --------------------------     variables declaration
    RecyclerView rv_month;
    ArrayList<Model_Month> monthArrayList;
    Adapter_Month adapterMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        // hide actionbar and statusBar
        getSupportActionBar().hide();

        // ---------------------------- variables initialization
        rv_month = findViewById(R.id.rv_month);
        rv_month.setHasFixedSize(true);
        rv_month.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        monthArrayList = new ArrayList<>();

        setmonthstatic();

    }

    private void setmonthstatic() {

        Model_Month model_month = new Model_Month();
        model_month.setMonth_name("January");
        monthArrayList.add(model_month);

        Model_Month model_month2 = new Model_Month();
        model_month2.setMonth_name("February");
        monthArrayList.add(model_month2);

        Model_Month model_month3 = new Model_Month();
        model_month3.setMonth_name("March");
        monthArrayList.add(model_month3);

        Model_Month model_month4 = new Model_Month();
        model_month4.setMonth_name("April");
        monthArrayList.add(model_month4);

        Model_Month model_month5 = new Model_Month();
        model_month5.setMonth_name("May");
        monthArrayList.add(model_month5);

        Model_Month model_month6 = new Model_Month();
        model_month6.setMonth_name("June");
        monthArrayList.add(model_month6);

        Model_Month model_month7 = new Model_Month();
        model_month7.setMonth_name("July");
        monthArrayList.add(model_month7);

        Model_Month model_month8 = new Model_Month();
        model_month8.setMonth_name("August");
        monthArrayList.add(model_month8);

        Model_Month model_month9 = new Model_Month();
        model_month9.setMonth_name("September");
        monthArrayList.add(model_month9);

        Model_Month model_month10 = new Model_Month();
        model_month10.setMonth_name("October");
        monthArrayList.add(model_month10);

        Model_Month model_month11 = new Model_Month();
        model_month11.setMonth_name("November");
        monthArrayList.add(model_month11);

        Model_Month model_month12 = new Model_Month();
        model_month12.setMonth_name("December");
        monthArrayList.add(model_month12);

        adapterMonth = new Adapter_Month(getApplicationContext(), monthArrayList);
        rv_month.setAdapter(adapterMonth);
        adapterMonth.notifyDataSetChanged();


    }
}