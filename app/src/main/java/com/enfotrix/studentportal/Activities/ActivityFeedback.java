package com.enfotrix.studentportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enfotrix.studentportal.Adapters.Adapter_Feedback;
import com.enfotrix.studentportal.Models.Model_Feedback;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityFeedback extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private Utils utils;


    List<Model_Feedback> list_Feedback = new ArrayList<>();

    RecyclerView recyc_Feedback;


    private EditText edt_head, edt_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        recyc_Feedback = findViewById(R.id.list_Feedback);
        recyc_Feedback.setHasFixedSize(true);
        recyc_Feedback.setLayoutManager(new LinearLayoutManager(this));
        edt_feedback = findViewById(R.id.edit_annBody);
//        edt_head = findViewById(R.id.edit_annHead);


        Button btn_announ = findViewById(R.id.btn_announcement);
        btn_announ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addFeedback(utils.getToken());


            }
        });

        fetchFeedback(utils.getToken());


        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                fetchFeedback(utils.getToken());
                pullToRefresh.setRefreshing(false);
            }
        });


    }

    private void fetchFeedback(String token) {

        final lottiedialog lottie = new lottiedialog(this);
        lottie.show();

        list_Feedback.clear();

        firestore.collection("Students").document(token)
                .collection("Feedback").orderBy("date", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Model_Feedback model_feedback = new Model_Feedback(
                                        document.getId(),
                                        document.getString("data"),
                                        document.getString("date"),
                                        document.getString("heading"));
                                list_Feedback.add(model_feedback);

                            }

                            Adapter_Feedback adapter_feedback = new Adapter_Feedback(list_Feedback);
                            recyc_Feedback.setAdapter(adapter_feedback);

                            lottie.dismiss();
                        } else {
                            lottie.dismiss();
                            Toast.makeText(ActivityFeedback.this, "something wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void addFeedback(String token) {


        final lottiedialog lottie = new lottiedialog(this);
        lottie.show();


        Map<String, Object> map = new HashMap<>();
        map.put("data", edt_feedback.getText().toString());
        map.put("date", getDate());
        map.put("heading", edt_head.getText().toString());


        firestore.collection("Students").document(token).collection("Feedback").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        lottie.dismiss();
                        Toast.makeText(ActivityFeedback.this, "Feedback submitted Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ActivityFeedback.this, MainActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lottie.dismiss();
                        Toast.makeText(ActivityFeedback.this, "Connection Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private String getDate() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

}