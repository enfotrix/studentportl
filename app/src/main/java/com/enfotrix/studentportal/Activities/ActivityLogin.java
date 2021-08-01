package com.enfotrix.studentportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class ActivityLogin extends AppCompatActivity {

    // --------------------------     variables declaration
    private FirebaseFirestore db;
    //private EditText txt_regNo, txt_password;
    private TextInputLayout edt_registerNumber, edt_password;
    private AppCompatButton btn_login;
    private TextView text_forgetpass, text_contactus;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ---------- hide actionbar and statusBar
        getSupportActionBar().hide();

        // ---------------------------- database and variables initialization
        db = FirebaseFirestore.getInstance();
        btn_login = findViewById(R.id.btn_login);
        edt_registerNumber = findViewById(R.id.edt_registerNumber);
        edt_password = findViewById(R.id.edt_password);
        text_forgetpass = findViewById(R.id.text_forgetpass);
        text_contactus = findViewById(R.id.text_contactus);

        utils = new Utils(this);

        edt_registerNumber.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_registerNumber.setError(null);
            }
        });
        edt_password.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_password.setError(null);
            }
        });

        // -------------------------- login button click event
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()){
                    auth(edt_registerNumber.getEditText().getText().toString().trim(), edt_password.getEditText().getText().toString().trim());
                }


            }
        });

        // ----------------------- forgot password link click
        text_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityForgetPasswordAuth.class);
                startActivity(intent);
                // finish();
            }
        });
    }

    //-------------- check empty fields
    private boolean checkEmpty() {
        Boolean isEmpty = false;
        if(edt_registerNumber.getEditText().getText().toString().trim().isEmpty())  edt_registerNumber.setError("Please Enter Registration #");
        else if(edt_password.getEditText().getText().toString().trim().isEmpty())    edt_password.setError("Please Enter Password");
        else isEmpty = true;
        return isEmpty;
    }

    // ---------------------------- login credentials authentication begin
    private void auth(String str_regNoTemp, String str_passwordTemp) {
        final lottiedialog lottie=new lottiedialog(this);
        lottie.show();
        db.collection("Students").whereEqualTo("student_regNo", str_regNoTemp).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getString("student_password").equals(str_passwordTemp)) {
                                utils.putToken(document.getId());
                                lottie.dismiss();
                                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                                intent.putExtra("documentID", document.getId());
                                startActivity(intent);
                                finish();
                            } else
                                edt_password.setError("Please Enter Correct Password");
                                lottie.dismiss();
                        }
                    } else
                        edt_registerNumber.setError("Please Enter Correct Registration #");
                    lottie.dismiss();
                }
            }
        });
    }
    // ---------------------------- login credentials authentication end
}
