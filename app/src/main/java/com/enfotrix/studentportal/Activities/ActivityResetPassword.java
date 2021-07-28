package com.enfotrix.studentportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.enfotrix.studentportal.R;
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

        Bundle bundle = getIntent().getExtras();
        String documentID = bundle.getString("documentID");

        //-------------------variables initialization
        edt_password_lay = findViewById(R.id.edt_password_lay);
        edt_re_pass_lay = findViewById(R.id.edt_re_pass_lay);
        btn_update = findViewById(R.id.btn_update);

        db = FirebaseFirestore.getInstance();


        //-------------------------- Reset Password button clicked
        btn_update.setOnClickListener(new View.OnClickListener() {
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
        map.put("student_password", edt_password_lay.getEditText().getText().toString().trim());
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