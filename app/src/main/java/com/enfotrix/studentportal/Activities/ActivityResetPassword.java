package com.enfotrix.studentportal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.enfotrix.studentportal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ActivityResetPassword extends AppCompatActivity {
    private TextView txt_newPassword,txt_newPasswordAgain;
    private Button btn_resetPassword;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Bundle bundle = getIntent().getExtras();
        String documentID = bundle.getString("documentID");

    //-------------------variables initialization
        txt_newPassword = findViewById(R.id.txt_newPassword);
        txt_newPasswordAgain = findViewById(R.id.txt_newPasswordAgain);
        btn_resetPassword = findViewById(R.id.btn_resetPassword);

        db = FirebaseFirestore.getInstance();


        //-------------------------- Reset Password button clicked
        btn_resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ActivityResetPassword.this, ""+regNo, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ActivityResetPassword.this, ""+fatherCNIC, Toast.LENGTH_SHORT).show();
                update(documentID);

            }
        });

    }

    private void update(String documentID) {
        Map<String, Object> map = new HashMap<>();
        map.put("student_password",txt_newPassword.getText().toString());
        db.collection("Students").document(documentID).update(map).
                addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                //Toast.makeText(ActivityResetPassword.this, ""+txt_newPassword, Toast.LENGTH_SHORT).show();
                Toast.makeText(ActivityResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityResetPassword.this, ActivityLogin.class);
                startActivity(intent);
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                    }
                });
    }

}