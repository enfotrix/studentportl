package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Adapters.Adapter_Result;
import com.enfotrix.studentportal.Models.Model_Result;
import com.enfotrix.studentportal.R;

import java.util.ArrayList;

public class ActivityResult extends AppCompatActivity {

    // def var
    RecyclerView rv_result;
    ArrayList<Model_Result> resultArrayList;
    Adapter_Result adapterResult;

    private TextView tv_resultObtainMarks, tv_examType, tv_resultDate, tv_resultGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        // iniviews
        IniViews();

        ResultAdapter();

    }

    private void ResultAdapter() {

        rv_result.setHasFixedSize(true);
        rv_result.setHasFixedSize(true);
        rv_result.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        resultArrayList = new ArrayList<>();

        Model_Result model_result = new Model_Result();
        model_result.setSub_name("English");
        model_result.setSub_marks("70/100");
        model_result.setSub_grade("B");
        resultArrayList.add(model_result);

        Model_Result model_result1 = new Model_Result();
        model_result1.setSub_name("Math");
        model_result1.setSub_marks("90");
        model_result1.setSub_grade("A+");
        resultArrayList.add(model_result1);

        adapterResult = new Adapter_Result(getApplicationContext(), resultArrayList);
        rv_result.setAdapter(adapterResult);
        adapterResult.notifyDataSetChanged();

    }

    private void IniViews() {

        rv_result = findViewById(R.id.rv_result);

        tv_resultObtainMarks = findViewById(R.id.tv_resultObtainMarks);
        tv_examType = findViewById(R.id.tv_examType);
        tv_resultDate = findViewById(R.id.tv_resultDate);
        tv_resultGrade = findViewById(R.id.tv_resultGrade);


    }
}