package com.enfotrix.studentportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ActivityResetPassword extends AppCompatActivity {

    private TextInputLayout edt_password_lay, edt_re_pass_lay;
    private AppCompatButton btn_update;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        // hide actionbar and statusBar
        getSupportActionBar().hide();

        //-------- get global variables
        Bundle bundle = getIntent().getExtras();
        String documentID = bundle.getString("documentID");

        //-------------------variables initialization
        edt_password_lay = findViewById(R.id.edt_password_lay);
        edt_re_pass_lay = findViewById(R.id.edt_re_pass_lay);
        btn_update = findViewById(R.id.btn_update);
        db = FirebaseFirestore.getInstance();

        //------hide error message from text layouts on focus
        edt_password_lay.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_password_lay.setError(null);
            }
        });
        edt_re_pass_lay.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_re_pass_lay.setError(null);
            }
        });

        //-------------------------- Reset Password button clicked
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty())       update(documentID);
            }
        });
    }
    // ---------------- check empty fields
    private Boolean checkEmpty() {
        Boolean isEmpty = false;
        if(edt_password_lay.getEditText().getText().toString().isEmpty())   edt_password_lay.setError("Please Enter Password");
        else if(edt_re_pass_lay.getEditText().getText().toString().isEmpty())     edt_re_pass_lay.setError("Please Enter Re Password");
        else if(!edt_password_lay.getEditText().getText().toString().equals(edt_re_pass_lay.getEditText().getText().toString()))     edt_re_pass_lay.setError("Passwords Don't Match");
        else isEmpty = true;
        return isEmpty;
    }

    private void update(String documentID) {
        final lottiedialog lottie=new lottiedialog(this);
        lottie.show();
        Map<String, Object> map = new HashMap<>();
        map.put("student_password",edt_password_lay.getEditText().getText().toString().trim());
        db.collection("Students").document(documentID).update(map).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        //Toast.makeText(ActivityResetPassword.this, ""+txt_newPassword, Toast.LENGTH_SHORT).show();
                       lottie.dismiss();
                        Toast.makeText(ActivityResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityResetPassword.this, ActivityLogin.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        lottie.dismiss();
                        Toast.makeText(ActivityResetPassword.this, ""+getResources().getString(R.string.str_somethingWentWrong), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}