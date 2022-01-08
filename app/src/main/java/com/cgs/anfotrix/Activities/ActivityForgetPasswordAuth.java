package com.cgs.anfotrix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.cgs.anfotrix.R;
import com.cgs.anfotrix.Lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class ActivityForgetPasswordAuth extends AppCompatActivity {

    // --------------------------     variables declaration
    private FirebaseFirestore db;
    //private EditText txt_regNo;
    //private EditText txt_fatherCNIC;
    private TextInputLayout edt_registerno_lay, edt_Fcnic_lay;
    private TextInputEditText edt_fcinc, edt_reg;
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
        edt_fcinc = findViewById(R.id.edt_fcinc);
        edt_reg = findViewById(R.id.edt_reg);


        edt_reg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cnic = charSequence.toString();
                if (charSequence.length() == 2 || charSequence.length() == 2) {
                    cnic += "-";
                    edt_reg.setText(cnic);
                    edt_reg.setSelection(cnic.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_fcinc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cnic = charSequence.toString();
                if (charSequence.length() == 5 || charSequence.length() == 13) {
                    cnic += "-";
                    edt_fcinc.setText(cnic);
                    edt_fcinc.setSelection(cnic.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //------hide error message from text layouts on focus
        edt_registerno_lay.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_registerno_lay.setError(null);
            }
        });
        edt_Fcnic_lay.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edt_Fcnic_lay.setError(null);
            }
        });

        // --------------------------- reset button click event
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty()) {
                    auth(edt_registerno_lay.getEditText().getText().toString().trim(), edt_Fcnic_lay.getEditText().getText().toString().trim());
                }
            }
        });
        //testing
    }

    // ---------------- check empty fields
    private boolean checkEmpty() {
        Boolean isEmpty = false;
        if (edt_registerno_lay.getEditText().getText().toString().isEmpty())
            edt_registerno_lay.setError("Please Enter Registration #");
        else if (edt_Fcnic_lay.getEditText().getText().toString().isEmpty()) {
            edt_Fcnic_lay.setError("Please Enter Father CNIC");
        } else isEmpty = true;
        return isEmpty;
    }


    // ---------------------------- authentication begin
    private void auth(String str_regNoTemp, String str_fatherCNICTemp) {
        final Lottiedialog lottie = new Lottiedialog(this);
        lottie.show();

        db.collection("Students").whereEqualTo("student_regNo", str_regNoTemp).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            if (document.getString("student_fatherCnic").equals(str_fatherCNICTemp)) {
                                lottie.dismiss();
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