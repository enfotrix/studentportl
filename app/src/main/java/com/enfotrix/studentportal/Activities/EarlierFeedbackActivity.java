package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EarlierFeedbackActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private Utils utils;


    List<Model_Feedback> list_Feedback = new ArrayList<>();

    RecyclerView recyc_Feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earlier_feedback);

        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        recyc_Feedback = findViewById(R.id.list_Feedback);
        recyc_Feedback.setHasFixedSize(true);
        recyc_Feedback.setLayoutManager(new LinearLayoutManager(this));

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
                            Toast.makeText(getApplicationContext(), "something wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}