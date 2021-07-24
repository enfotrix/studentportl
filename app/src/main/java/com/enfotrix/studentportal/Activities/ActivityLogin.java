package com.enfotrix.studentportal.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class ActivityLogin extends AppCompatActivity {

        // --------------------------     variables declaration
        private FirebaseFirestore db;
        private EditText txt_regNo,txt_password;
        private Button btn_login;
        private TextView txt_forgotPassword;
        private Utils utils;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            // ---------------------------- database and variables initialization
            db = FirebaseFirestore.getInstance();
            txt_regNo = findViewById(R.id.txt_regNo);
            txt_password = findViewById(R.id.txt_password);
            btn_login = findViewById(R.id.btn_login);
            txt_forgotPassword  = findViewById(R.id.txt_forgotPassword);
            utils = new Utils(this);


            // -------------------------- login button click event
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    auth(txt_regNo.getText().toString(),txt_password.getText().toString());

                }
            });

            // ----------------------- forgot password link click
            txt_forgotPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityLogin.this, ActivityForgetPasswordAuth.class);
                    startActivity(intent);
                    // finish();
                }
            });

            // ----------------------- Register Now link click
//            txt_registerNow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(ActivityLogin.this, ActivitySignup.class);
//                    startActivity(intent);
//                    // finish();
//                }
//            });
        }

        // ---------------------------- login credentials authentication begin
        private void auth(String str_regNo, String str_password) {
//            Toast.makeText(ActivityLogin.this, ""+str_regNo, Toast.LENGTH_SHORT).show();
//            Toast.makeText(ActivityLogin.this, ""+str_password, Toast.LENGTH_SHORT).show();
            db.collection("Students").whereEqualTo("student_regNo",str_regNo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        if(!task.getResult().isEmpty()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getString("student_password").equals(str_password)){
                                    utils.putToken(document.getId());
                                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else Toast.makeText(ActivityLogin.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(ActivityLogin.this, "Registration No Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // ---------------------------- login credentials authentication end
    }
