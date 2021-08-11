package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Adapters.Adapter_Result;
import com.enfotrix.studentportal.Models.Model_Result;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityResult extends AppCompatActivity {

    // def var
    RecyclerView rv_result;
    ArrayList<Model_Result> resultArrayList;
    Adapter_Result adapterResult;
    private FirebaseFirestore db;

    private TextView tv_resultObtainMarks, tv_examType, tv_resultDate, tv_resultGrade;
    private String classID, sectionId, sessionname, examtype;
    List<String> sub_list;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        // iniviews
        IniViews();

        // ini db
        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);
        sub_list = new ArrayList<>();


        sessionname = getIntent().getStringExtra("session");
        examtype = getIntent().getStringExtra("examtype");
        sectionId = getIntent().getStringExtra("classid");
        classID = getIntent().getStringExtra("classgrade");

        rv_result.setHasFixedSize(true);
        rv_result.setHasFixedSize(true);
        rv_result.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        resultArrayList = new ArrayList<>();


//        Toast.makeText(this, "" + sessionname + examtype + classid, Toast.LENGTH_SHORT).show();

        fetchsubjects(sectionId, classID);
        ResultAdapter();


    }

    private void fetchsubjects(String sectionId, String classID) {
//
//        Toast.makeText(this, "" + sectionId, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "" + classID, Toast.LENGTH_SHORT).show();

        db.collection("Class").document(classID)
                .collection("Section").document(sectionId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        if (task.isSuccessful()) {

//                            Toast.makeText(ActivityResult.this, "yess", Toast.LENGTH_SHORT).show();

                            sub_list = (List<String>) document.get("Subjects");
//
//                            Toast.makeText(ActivityResult.this, "" + sub_list, Toast.LENGTH_SHORT).show();


                            for (int i = 0; i < sub_list.size(); i++) {

                                db.collection("Exams").document(sessionname)
                                        .collection("Type").document(examtype)
                                        .collection("classes").document(sectionId)
                                        .collection("Subjects").document(sub_list.get(i))
                                        .collection("Students").document(utils.getToken())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult();

                                                    String obtainmarks = documentSnapshot.getString("obtainMarks");
                                                    String totalmarks = documentSnapshot.getString("totalMarks");


                                                    Toast.makeText(ActivityResult.this, "" + obtainmarks + totalmarks, Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                            }


                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void ResultAdapter() {


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


    }

    private void IniViews() {

        rv_result = findViewById(R.id.rv_result);

        tv_resultObtainMarks = findViewById(R.id.tv_resultObtainMarks);
        tv_examType = findViewById(R.id.tv_examType);
        tv_resultDate = findViewById(R.id.tv_resultDate);
        tv_resultGrade = findViewById(R.id.tv_resultGrade);


    }
}