package com.enfotrix.studentportal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.enfotrix.studentportal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class ActivityForgetPasswordAuth extends AppCompatActivity {

    // --------------------------     variables declaration
    private FirebaseFirestore db;
    private EditText txt_regNo;
    private EditText txt_fatherCNIC;
    private TextInputLayout edt_registerno_lay, edt_Fcnic_lay;
    private AppCompatButton btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password_auth);
        // hide actionbar and statusBar
        getSupportActionBar().hide();
        // ---------------------------- database and variables initialization
        db = FirebaseFirestore.getInstance();

        btn_verify = findViewById(R.id.btn_verify);
        edt_registerno_lay = findViewById(R.id.edt_registerno_lay);
        edt_Fcnic_lay = findViewById(R.id.edt_Fcnic_lay);

        // --------------------------- reset button click event
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth(txt_regNo.getText().toString(), txt_fatherCNIC.getText().toString());
            }
        });
        //testing
    }


    // ---------------------------- authentication begin
    private void auth(String str_regNo, String str_fatherCNIC) {
//            Toast.makeText(ActivityLogin.this, ""+str_regNo, Toast.LENGTH_SHORT).show();
//            Toast.makeText(ActivityLogin.this, ""+str_password, Toast.LENGTH_SHORT).show();
        db.collection("Students").whereEqualTo("student_regNo", str_regNo).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getString("student_fatherCnic").equals(str_fatherCNIC)) {
                                Intent intent = new Intent(ActivityForgetPasswordAuth.this, ActivityResetPassword.class);
                                intent.putExtra("documentID", document.getId());
                                startActivity(intent);
                                finish();

                            } else
                                Toast.makeText(ActivityForgetPasswordAuth.this, "CNIC Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(ActivityForgetPasswordAuth.this, "Registration No Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}