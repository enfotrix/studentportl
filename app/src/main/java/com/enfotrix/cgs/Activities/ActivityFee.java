package com.enfotrix.cgs.Activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.enfotrix.cgs.Adapters.Adapter_fee;
import com.enfotrix.cgs.Models.Model_Fee;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityFee extends AppCompatActivity {

    List<Model_Fee> feeArrayList = new ArrayList<>();
    RecyclerView recyclerViewfee;
    Adapter_fee adapter_fee;
    FirebaseFirestore firestore;
    Utils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);


        getSupportActionBar().hide();

        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(this);

        recyclerViewfee = findViewById(R.id.recyclerView_fee);
        recyclerViewfee.setHasFixedSize(true);
        recyclerViewfee.setLayoutManager(new LinearLayoutManager(this));


        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swiperefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                fetchNotifi();
                pullToRefresh.setRefreshing(false);
            }
        });


        String session = getIntent().getStringExtra("attendanceSession");
        String month = getIntent().getStringExtra("month");

        getMonthFee(getIntent().getStringExtra("attendanceSession"), getIntent().getStringExtra("month"));


        Toast.makeText(this, month + "    " + session, Toast.LENGTH_SHORT).show();


    }

    private void getMonthFee(String attendanceSession, String month) {

        final lottiedialog lottie = new lottiedialog(this);
        lottie.show();

        firestore.collection("Fee").document(attendanceSession)
                .collection("Month").document(month)
                .collection("StudentsFee").document(utils.getToken())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {


                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();
                            String status = "";
                            status = documentSnapshot.getString("status");
                            if (status != null) {

                                Model_Fee model_fee = new Model_Fee();
                                model_fee.setMonth(documentSnapshot.getString(""));
                                model_fee.setFee(documentSnapshot.getString("amount"));
                                model_fee.setStatus(status);
                                feeArrayList.add(model_fee);

                            }
                        }

                        adapter_fee = new Adapter_fee(getApplicationContext(), feeArrayList);
                        recyclerViewfee.setAdapter(adapter_fee);
                        adapter_fee.notifyDataSetChanged();

                        lottie.dismiss();
                    }
                });
    }
}