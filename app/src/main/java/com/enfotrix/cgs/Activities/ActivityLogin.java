package com.enfotrix.cgs.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.Lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {

    // --------------------------     variables declaration
    private FirebaseFirestore db;
    //private EditText txt_regNo, txt_password;
    private TextInputLayout edt_registerNumber, edt_password;
    private AppCompatButton btn_login;
    private TextView text_forgetpass, text_contactus;
    private Utils utils;
    private ImageView img_cLogo;
    private TextInputEditText edtt_reg;

    private FirebaseFirestore firestore;

    private String cu_departmentName1, cu_email1, cu_mobileNo1, cu_whatsapp1, cu_landline1;
    private String cu_departmentName2, cu_email2, cu_mobileNo2, cu_whatsapp2, cu_landline2;
    private String cu_departmentName3, cu_email3, cu_mobileNo3, cu_whatsapp3, cu_landline3;
    private ArrayList<String> departmentname;
    private String contact_num, contact_email, contact_landline, contact_whatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // ---------- hide actionbar and statusBar
        getSupportActionBar().setTitle("Login");

        // ---------------------------- database and variables initialization
        db = FirebaseFirestore.getInstance();
        btn_login = findViewById(R.id.btn_login);
        edt_registerNumber = findViewById(R.id.edt_registerNumber);
        edt_password = findViewById(R.id.edt_password);
        text_forgetpass = findViewById(R.id.text_forgetpass);
        img_cLogo = findViewById(R.id.img_cLogo);
        edtt_reg = findViewById(R.id.edtt_reg);
//        text_contactus = findViewById(R.id.text_contactus);

        utils = new Utils(this);
        firestore = FirebaseFirestore.getInstance();
        departmentname = new ArrayList<>();

//        fetchdepartment();


        edtt_reg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String cnic = charSequence.toString();
                if (charSequence.length() == 2 ) {
                    cnic += "-";
                    edtt_reg.setText(cnic);
                    edtt_reg.setSelection(cnic.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        img_cLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://enfotrix.com/"));
                startActivity(browserIntent);
            }
        });

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
                if (checkEmpty()) {
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

    private void fetchContacts() {

        final Lottiedialog lottie = new Lottiedialog(this);
        lottie.show();
        firestore.collection("ContactUs").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {


                                if (document.getString("cu_departmentName").equals("Principal")) {

                                    cu_departmentName1 = document.getString("cu_departmentName");
                                    cu_email1 = document.getString("cu_email");
                                    cu_mobileNo1 = document.getString("cu_mobileNo");
                                    cu_whatsapp1 = document.getString("cu_whatsapp");
                                    cu_landline1 = document.getString("cu_landline");
                                }

                                if (document.getString("cu_departmentName").equals("Accounts")) {

                                    cu_departmentName2 = document.getString("cu_departmentName");
                                    cu_email2 = document.getString("cu_email");
                                    cu_mobileNo2 = document.getString("cu_mobileNo");
                                    cu_whatsapp2 = document.getString("cu_whatsapp");
                                    cu_landline2 = document.getString("cu_landline");
                                }

                                if (document.getString("cu_departmentName").equals("Administration")) {

                                    cu_departmentName3 = document.getString("cu_departmentName");
                                    cu_email3 = document.getString("cu_email");
                                    cu_mobileNo3 = document.getString("cu_mobileNo");
                                    cu_whatsapp3 = document.getString("cu_whatsapp");
                                    cu_landline3 = document.getString("cu_landline");
                                }


                            }
                            lottie.dismiss();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        lottie.dismiss();
                        Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void fetchdepartment() {

        db.collection("ContactUs").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                departmentname.add(document.getId());


                                Toast.makeText(getApplicationContext(), "" + departmentname, Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getContext(), "Debug", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "" + document.getId(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
    }

    private void contactUs() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View attendancedialog = getLayoutInflater().inflate(R.layout.department_dialog, null);

        AutoCompleteTextView txtContact = attendancedialog.findViewById(R.id.txtContact);
        AppCompatButton btn_Contact = attendancedialog.findViewById(R.id.btn_Contact);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), R.layout.dropdown_department, departmentname);
//        txtContact.setText(arrayAdapter.getItem(0).toString(), false);
        txtContact.setAdapter(arrayAdapter);

        btn_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheet(txtContact.getText().toString().trim());

            }
        });

        builder.setView(attendancedialog);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void bottomsheet(String dptName) {

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_contacts, null);


        TextView txt_cu_departmentName1 = vie.findViewById(R.id.txt_pri);
        ImageView img_cu_call1 = vie.findViewById(R.id.img_cu_call1);
        ImageView img_cu_landline1 = vie.findViewById(R.id.img_cu_landline1);
        ImageView img_cu_mail1 = vie.findViewById(R.id.img_cu_mail1);
        ImageView img_cu_whatsapp1 = vie.findViewById(R.id.img_cu_whatsapp1);
        CardView cv_mobile = vie.findViewById(R.id.cv_mobile);
        CardView cv_landline = vie.findViewById(R.id.cv_landline);
        CardView cv_whatsapp = vie.findViewById(R.id.cv_whatsapp);
        CardView cv_email = vie.findViewById(R.id.cv_email);


        db.collection("ContactUs").document(dptName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {

                            DocumentSnapshot documentSnapshot = task.getResult();
                            cu_mobileNo1 = documentSnapshot.getString("cell");
                            cu_email1 = documentSnapshot.getString("email");
                            cu_landline1 = documentSnapshot.getString("landline");
                            cu_departmentName1 = documentSnapshot.getString("tittle");
                            txt_cu_departmentName1.setText(cu_departmentName1);

                            cu_whatsapp1 = documentSnapshot.getString("whatsapp");

                            /////////////////////////////////////////
                            if (cu_mobileNo1 != null) {
                                contact_num = cu_mobileNo1;
                            } else {
                                cv_mobile.setVisibility(View.GONE);
                            }
                            ////////////////////////////////////////////
                            if (cu_email1 != null) {
                                contact_email = cu_email1;
                            } else {
                                cv_email.setVisibility(View.GONE);
                            }
                            /////////////////////////////////////////////
                            if (cu_whatsapp1 != null) {
                                contact_whatsapp = cu_whatsapp1;
                            } else {
                                cv_whatsapp.setVisibility(View.GONE);
                            }
                            /////////////////////////////////////////
                            if (cu_landline1 != null) {
                                contact_landline = cu_landline1;
                            } else {
                                cv_landline.setVisibility(View.GONE);
                            }


//                            Toast.makeText(getContext(), "" + cellnumber + email + landline + tittle + whatsapp, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        dialog.setContentView(vie);
        dialog.setCancelable(true);
        dialog.show();

        img_cu_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact_num));
                startActivity(i);

            }
        });

        img_cu_landline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact_landline));
                startActivity(i);
            }
        });

        img_cu_mail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", contact_email, null));
                //i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                //i.putExtra(Intent.EXTRA_TEXT, BODY);

            }
        });

        img_cu_whatsapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "https://api.whatsapp.com/send?phone=" + contact_whatsapp;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

    }

    //-------------- check empty fields
    private boolean checkEmpty() {
        Boolean isEmpty = false;
        if (edt_registerNumber.getEditText().getText().toString().trim().isEmpty())
            edt_registerNumber.setError("Please Enter Registration #");
        else if (edt_password.getEditText().getText().toString().trim().isEmpty())
            edt_password.setError("Please Enter Password");
        else isEmpty = true;
        return isEmpty;
    }

    // ---------------------------- login credentials authentication begin
    private void auth(String str_regNoTemp, String str_passwordTemp) {
        final Lottiedialog lottie = new Lottiedialog(this);
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
