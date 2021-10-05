package com.enfotrix.cgs.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enfotrix.cgs.Adapters.Adapter_Announ;
import com.enfotrix.cgs.Models.Model_Announ;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityAnnouncement extends AppCompatActivity {


    private FirebaseFirestore firestore;
    private Utils utils;


    List<Model_Announ> list_Announ = new ArrayList<>();
    RecyclerView recyc_Announ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        recyc_Announ = findViewById(R.id.list_Announ);
        recyc_Announ.setHasFixedSize(true);
        recyc_Announ.setLayoutManager(new LinearLayoutManager(this));


        fetchNotifi(utils.getToken());

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNotifi(utils.getToken());
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void fetchNotifi(String userID) {


        final lottiedialog lottie = new lottiedialog(this);
        lottie.show();

        list_Announ.clear();
        firestore.collection("Announcement").orderBy("date", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Model_Announ model_Announ = new Model_Announ(
                                        document.getId(),
                                        document.getString("data"),
                                        document.getString("date"),
                                        document.getString("heading"));
                                list_Announ.add(model_Announ);

                            }


                            Adapter_Announ adapter_Announ = new Adapter_Announ(list_Announ);
                            recyc_Announ.setAdapter(adapter_Announ);

                            lottie.dismiss();
                        } else {
                            lottie.dismiss();
                            Toast.makeText(ActivityAnnouncement.this, "Connection Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        lottie.dismiss();
                        Toast.makeText(ActivityAnnouncement.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }


}