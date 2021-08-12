package com.enfotrix.studentportal.Activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enfotrix.studentportal.Adapters.Adapter_Image;
import com.enfotrix.studentportal.Models.Model_Image;
import com.enfotrix.studentportal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ActivityGallery extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private List<Model_Image> list;
    private FirebaseFirestore db;
    //private DatabaseReference reference;
    private Adapter_Image adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // hide actionbar and statusBar
        getSupportActionBar().setTitle("Gallery");

        list = new ArrayList<Model_Image>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(ActivityGallery.this, 3));
        progressBar = findViewById(R.id.progressBar);
        db = FirebaseFirestore.getInstance();
        //reference = FirebaseDatabase.getInstance().getReference().child("images");

        getData();
    }

    private void getData() {


        db.collection("Gallery").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Toast.makeText(ActivityGallery.this, "" + document.getString("path"), Toast.LENGTH_SHORT).show();
                                Model_Image model_image = new Model_Image(
                                        document.getId(),
                                        document.getString("path")
                                );
                                list.add(model_image);
                            }
                            Adapter_Image adapter_image = new Adapter_Image(ActivityGallery.this, list);
                            recyclerView.setAdapter(adapter_image);
                        }
                    }
                });
    }
}