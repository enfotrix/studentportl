package com.enfotrix.cgs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.cgs.Adapters.Adapter_Feedback;
import com.enfotrix.cgs.Models.Model_Feedback;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.Lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class ActivityFeedback extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private Utils utils;


    List<Model_Feedback> list_Feedback = new ArrayList<>();

    RecyclerView recyc_Feedback;


    private EditText edt_head;
    private TextInputLayout edt_feedback;
    private TextInputEditText tv_feedbck;

    private AppCompatButton btn_earler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        btn_earler = findViewById(R.id.btn_earler);
        tv_feedbck = findViewById(R.id.tv_feedbck);

//        recyc_Feedback = findViewById(R.id.list_Feedback);
//        recyc_Feedback.setHasFixedSize(true);
//        recyc_Feedback.setLayoutManager(new LinearLayoutManager(this));

        edt_feedback = findViewById(R.id.edit_annBody);
//        edt_head = findViewById(R.id.edit_annHead);


        btn_earler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EarlierFeedbackActivity.class));
            }
        });
        Button btn_announ = findViewById(R.id.btn_announcement);
        btn_announ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkEmpty()) {

//                    Toast.makeText(getApplicationContext(), "Please Write Feedback", Toast.LENGTH_SHORT).show();
                    logout();
                }


            }
        });

//        fetchFeedback(utils.getToken());
//
//
//        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
//        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                fetchFeedback(utils.getToken());
//                pullToRefresh.setRefreshing(false);
//            }
//        });


    }

    public void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(this)
                .setTitle("FeedBack")
                .setMessage("Are you sure want to Submit!")
                .setCancelable(false)
                .setPositiveButton("Submit", R.drawable.ic_baseline_logout_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();

                        addFeedback(utils.getToken());
//                        utils.logout();
//                        startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
//                        finish();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_baseline_cancel_presentation_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void fetchFeedback(String token) {

        final Lottiedialog lottie = new Lottiedialog(this);
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


        final Lottiedialog lottie = new Lottiedialog(this);
        lottie.show();


        Map<String, Object> map = new HashMap<>();
        map.put("data", edt_feedback.getEditText().getText().toString());
        map.put("date", getDate());
        map.put("heading", "Feedback");


        firestore.collection("Students").document(token).collection("Feedback").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        lottie.dismiss();
                        tv_feedbck.setText(null);
                        Toast.makeText(ActivityFeedback.this, "Feedback submitted Successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ActivityFeedback.this, MainActivity.class));
//                        finish();

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

    private boolean checkEmpty() {
        Boolean isEmpty = false;
        if (edt_feedback.getEditText().getText().toString().trim().isEmpty())
            Toast.makeText(getApplicationContext(), "Please write feedback", Toast.LENGTH_SHORT).show();
        else isEmpty = true;
        return isEmpty;
    }

}