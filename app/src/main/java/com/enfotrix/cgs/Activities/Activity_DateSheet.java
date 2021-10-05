package com.enfotrix.cgs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.cgs.Adapters.Adapter_DateSheet;
import com.enfotrix.cgs.Models.Model_DateSheet;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Activity_DateSheet extends AppCompatActivity {

    private TextView tv_class, tv_classSection;

    private RecyclerView rv_dateSheet;
    private Adapter_DateSheet adapterDateSheet;
    ArrayList<Model_DateSheet> dateSheetArrayList;

    private FirebaseFirestore db;
    private Utils utils;
    private String sessionname, examtype;
    private String sectioID, classgrade;
    private List<String> sub_list;
    private String classsection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_sheet);

        getSupportActionBar().hide();

        // ini views
        IniViews();

        db = FirebaseFirestore.getInstance();
        utils = new Utils(this);
        sub_list = new ArrayList<>();
        dateSheetArrayList = new ArrayList<>();

        rv_dateSheet.setHasFixedSize(true);
        rv_dateSheet.setHasFixedSize(true);
        rv_dateSheet.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        db.collection("Students").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        sectioID = document.getString("student_classID");
                        classgrade = document.getString("class_grade");
                        // fetchclasssection(document.getString("student_classID"), document.getString("class_grade"));
                        fetchdatesheet(getIntent().getStringExtra("session"), getIntent().getStringExtra("examtype"), document.getString("student_classID"), document.getString("class_grade"));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();

                    }
                });

        sessionname = getIntent().getStringExtra("session");
        examtype = getIntent().getStringExtra("examtype");


//        Toast.makeText(this, ""+sessionname+examtype, Toast.LENGTH_SHORT).show();

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

                        // classsection = document.getString("student_classID") + "-" + document.getString("class_grade");
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
                            classsection = documentSnapshot.getString("class_grade") + "-" + documentSnapshot.getString("class_section");


                            tv_classSection.setText(documentSnapshot.getString("class_grade") + " - " + documentSnapshot.getString("class_section"));
                            // txt_studentClass.setText(documentSnapshot.getString("class_grade") + "-" + documentSnapshot.getString("class_section"));
                            //txt_studentmedium.setText("" + documentSnapshot.getString("class_medium"));
                        }
                    }
                });
    }


    private void fetchdatesheet(String session, String examtype, String sectioID, String classgrade) {

//        Toast.makeText(this, ""+sectioID, Toast.LENGTH_SHORT).show();
       /* db.collection("Students").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();

                            String classgrade = document.getString("student_classID");

                            Toast.makeText(getApplicationContext(), "" + classgrade, Toast.LENGTH_SHORT).show();



                        }
                    }
                });
*/

        db.collection("Class").document(classgrade)
                .collection("Section").document(sectioID).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        if (task.isSuccessful()) {

                            sub_list = (List<String>) document.get("Subjects");

                            //Toast.makeText(Activity_DateSheet.this, "" + sub_list, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < sub_list.size(); i++) {


                                db.collection("DateSheet").document(session)
                                        .collection("ExamType").document(examtype)
                                        .collection("Class").document(classgrade)
                                        .collection("Subjects").document(sub_list.get(i))
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    //Toast.makeText(Activity_DateSheet.this, "QuerySnapshot", Toast.LENGTH_SHORT).show();
                                                    DocumentSnapshot documentSnapshot = task.getResult();
                                                    String examdate = documentSnapshot.getString("examDate");
//                                                    String substingdate = examdate.substring(3, 5);
                                                    String subname = documentSnapshot.getString("subjectName");
//                                                    Toast.makeText(Activity_DateSheet.this, "" + substingdate, Toast.LENGTH_SHORT).show();

                                                    if (examdate != null && subname != null) {
                                                        Model_DateSheet model_dateSheet = new Model_DateSheet();
                                                        model_dateSheet.setSubName(subname);
                                                        model_dateSheet.setClasssession(session);
                                                        model_dateSheet.setClasssectin(classsection);
                                                        dateSheetArrayList.add(model_dateSheet);
                                                        // Toast.makeText(Activity_DateSheet.this, "" + classsection, Toast.LENGTH_SHORT).show();
                                                    }
//                                                    String examdate = documentSnapshot.getString("");
                                                    //Toast.makeText(Activity_DateSheet.this, "Deubug", Toast.LENGTH_SHORT).show();
//                                                    Toast.makeText(Activity_DateSheet.this, "" + documentSnapshot.getString("examDate"), Toast.LENGTH_SHORT).show();

                                                }

                                                adapterDateSheet = new Adapter_DateSheet(getApplicationContext(), dateSheetArrayList);
                                                rv_dateSheet.setAdapter(adapterDateSheet);
                                                adapterDateSheet.notifyDataSetChanged();
                                            }
                                        });


                            }
                        }
                    }
                });


    }

    private void IniViews() {

        tv_class = findViewById(R.id.tv_class);
        tv_classSection = findViewById(R.id.tv_classSection);

        rv_dateSheet = findViewById(R.id.rv_dateSheet);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}