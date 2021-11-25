package com.enfotrix.cgs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.cgs.Adapters.Adapter_Month;
import com.enfotrix.cgs.Adapters.Adapter_attendance;
import com.enfotrix.cgs.Lottiedialog;
import com.enfotrix.cgs.Models.Model_Attendance;
import com.enfotrix.cgs.Models.Model_Month;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAttendance extends AppCompatActivity implements Adapter_Month.OnItemClickListener {

    // --------------------------     variables declaration
    private RecyclerView rv_month, rv_attendance;
    private ArrayList<Model_Month> monthArrayList;
    private ArrayList<Model_Attendance> attendanceArrayList;
    private Adapter_Month adapterMonth;
    private Adapter_attendance adapterAttendance;
    private AutoCompleteTextView autoCompletetxt;
    private Calendar calendar;
    private AutoCompleteTextView txtattendance;
    private AppCompatButton btn_attendance;
    private ImageView imgCalender;
    private TextView tv_totalPresent, tv_totalAbsent, tv_totalLeave;
    private String fromatedate;
    private String attenadnce_session, attenadnce_date, section_ID;
    private FirebaseFirestore db;
    private Utils utils;
    private String month;
    private int total_p = 0;
    private int total_a = 0;
    private int total_l = 0;
    private int days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        // hide actionbar and statusBar
        getSupportActionBar().hide();

        // ---------------------------- variables initialization
        autoCompletetxt = findViewById(R.id.autoCompletetxt);
        tv_totalPresent = findViewById(R.id.tv_totalPresent);
        tv_totalAbsent = findViewById(R.id.tv_totalAbsent);
        tv_totalLeave = findViewById(R.id.tv_totalLeave);
        rv_month = findViewById(R.id.rv_month);
        rv_month.setHasFixedSize(true);
        rv_month.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        rv_attendance = findViewById(R.id.rv_attendance);
        rv_attendance.setHasFixedSize(true);
        rv_attendance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        monthArrayList = new ArrayList<>();
        attendanceArrayList = new ArrayList<>();

        calendar = Calendar.getInstance();

        attenadnce_session = getIntent().getStringExtra("attendanceSession");
        attenadnce_date = getIntent().getStringExtra("date");
        section_ID = getIntent().getStringExtra("classid");

        //Toast.makeText(this, "" + attenadnce_session + attenadnce_date, Toast.LENGTH_SHORT).show();


//        dropdownmenue();
        setmonthstatic();
//        fetchattendance(attenadnce_session, attenadnce_date, section_ID);

        //Toast.makeText(this, section_ID, Toast.LENGTH_SHORT).show();
        fetchattendance(getIntent().getStringExtra("attendanceSession"), "01");


    }

    private void fetchattendance(String attenadnce_session, String month) {

        final Lottiedialog lottie = new Lottiedialog(this);
        lottie.show();

        total_p = 0;
        total_a = 0;
        total_l = 0;
        tv_totalAbsent.setText("0");
        tv_totalPresent.setText("0");
        tv_totalLeave.setText("0");

        attendanceArrayList.clear();


        days = 1;
        String tempday = "";

        while (days <= 31) {
            if (days < 10) tempday = "0" + days;
            else tempday = "" + days;
            String date = tempday + "-" + month + "-" + attenadnce_session.substring(0, 4);

            db.collection("Attendance").document(attenadnce_session)
                    .collection("Date").document(date)
                    .collection("Class").document(getIntent().getStringExtra("classid"))
                    .collection("Attende").document(utils.getToken())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(ActivityAttendance.this, "Debug", Toast.LENGTH_SHORT).show();
                                DocumentSnapshot document = task.getResult();
                                String status = "";
                                status = document.getString("status");
                                if (status != null) {

                                    Model_Attendance model_attendance = new Model_Attendance();
                                    model_attendance.setDate(date);
                                    model_attendance.setStatus(status);
                                    attendanceArrayList.add(model_attendance);

                                    if (document.getString("status").equals("Present")) {
                                        total_p++;
                                        tv_totalPresent.setText(String.valueOf(total_p));

                                    }

                                    if (document.getString("status").equals("Leave")) {
                                        total_l++;
                                        tv_totalLeave.setText(String.valueOf(total_l));

                                    }

                                    if (document.getString("status").equals("Absent")) {
                                        total_a++;
                                        tv_totalAbsent.setText(String.valueOf(total_a));

                                    }


                                }

                                //Toast.makeText(ActivityAttendance.this, status, Toast.LENGTH_SHORT).show();
                            }


                            adapterAttendance = new Adapter_attendance(getApplicationContext(), attendanceArrayList);
                            rv_attendance.setAdapter(adapterAttendance);
                            adapterAttendance.notifyDataSetChanged();
                            if (days >= 29)
                                lottie.dismiss();
                        }

                    });


            days++;


        }


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
        adapterMonth.setOnItemClickListener(this);
        adapterMonth.notifyDataSetChanged();


    }

    @Override
    public void onItemClicks(int position) {
        final Model_Month model_month = monthArrayList.get(position);

        position++;
        if (position < 10) month = "0" + position;
        else month = "" + position;

        fetchattendance(attenadnce_session, month);

        //Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}