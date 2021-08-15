package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Adapters.Adapter_TimeTable;
import com.enfotrix.studentportal.Models.Model_TimeTable;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ActivityTimeTable extends AppCompatActivity {

    private RecyclerView rv_timeTable;
    private TextView tv_classSection;
    private FirebaseFirestore db;
    private Utils utils;
    private ArrayList<Model_TimeTable> timeTableArrayList;
    private Adapter_TimeTable adapterTimeTable;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        // hide actionbar and statusBar
        getSupportActionBar().hide();

        // ini views
        IniViews();

        rv_timeTable.setHasFixedSize(true);
        rv_timeTable.setHasFixedSize(true);
        rv_timeTable.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        timeTableArrayList = new ArrayList<>();


        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        fetchtimetable();
        getData();

    }

    public void getData() {
        final lottiedialog lottie = new lottiedialog(this);
        lottie.show();
        db.collection("Students").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        fetchclasssection(document.getString("student_classID"), document.getString("class_grade"));

                        lottie.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        lottie.dismiss();
                    }
                });
    }

    private void fetchclasssection(String classid, String classgrade) {

        db.collection("Class").document(classgrade)
                .collection("Section").document(classid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            documentSnapshot.getString("class_medium");
                            documentSnapshot.getString("class_section");
                            documentSnapshot.getString("class_grade");

                            tv_classSection.setText(documentSnapshot.getString("class_grade") + " - " + documentSnapshot.getString("class_section"));
                            // txt_studentClass.setText(documentSnapshot.getString("class_grade") + "-" + documentSnapshot.getString("class_section"));
                            //txt_studentmedium.setText("" + documentSnapshot.getString("class_medium"));
                        }
                    }
                });
    }

    private void fetchtimetable() {

        final lottiedialog lottie = new lottiedialog(this);
        lottie.show();

        db.collection("Students").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        String sectionID = document.getString("student_classID");

                        Toast.makeText(ActivityTimeTable.this, "" + sectionID, Toast.LENGTH_SHORT).show();

                        db.collection("TimeTable").document(sectionID)
                                .collection("Slots").get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                                        if (task.isSuccessful()) {

                                            for (QueryDocumentSnapshot Snapshot : task.getResult()) {

                                                String slotnumber = Snapshot.getString("slotNumber");
                                                String slotname = Snapshot.getString("slotName");
                                                String slottime = Snapshot.getString("slotTime");

                                                Toast.makeText(ActivityTimeTable.this, "" + slotnumber + slotname + slottime, Toast.LENGTH_SHORT).show();

                                                Model_TimeTable model_timeTable = new Model_TimeTable();
                                                model_timeTable.setTv_slotNumber(slotnumber);
                                                model_timeTable.setTv_subName(slotname);
                                                model_timeTable.setTv_startTime(slottime);
                                                timeTableArrayList.add(model_timeTable);

                                            }

                                        }

                                        adapterTimeTable = new Adapter_TimeTable(getApplicationContext(), timeTableArrayList);
                                        rv_timeTable.setAdapter(adapterTimeTable);
                                        adapterTimeTable.notifyDataSetChanged();

                                    }
                                });

                        lottie.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        lottie.dismiss();
                    }
                });
    }

    private void IniViews() {

        rv_timeTable = findViewById(R.id.rv_timeTable);

        tv_classSection = findViewById(R.id.tv_classSection);

    }
}