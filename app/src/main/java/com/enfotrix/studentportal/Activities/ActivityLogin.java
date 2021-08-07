package com.enfotrix.studentportal.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

    private FirebaseFirestore firestore;

    private String cu_departmentName1, cu_email1, cu_mobileNo1, cu_whatsapp1, cu_landline1;
    private String cu_departmentName2, cu_email2, cu_mobileNo2, cu_whatsapp2, cu_landline2;
    private String cu_departmentName3, cu_email3, cu_mobileNo3, cu_whatsapp3, cu_landline3;


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
        firestore = FirebaseFirestore.getInstance();


        text_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheet();
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

        final lottiedialog lottie = new lottiedialog(this);
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

    private void bottomsheet() {


        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_contacts, null);


        TextView txt_cu_departmentName1 = vie.findViewById(R.id.txt_pri);
        txt_cu_departmentName1.setText(cu_departmentName1);
        ImageView img_cu_call1 = vie.findViewById(R.id.img_cu_call1);
        ImageView img_cu_landline1 = vie.findViewById(R.id.img_cu_landline1);
        ImageView img_cu_mail1 = vie.findViewById(R.id.img_cu_mail1);
        ImageView img_cu_whatsapp1 = vie.findViewById(R.id.img_cu_whatsapp1);

        TextView txt_cu_departmentName2 = vie.findViewById(R.id.txt_admin);
//        txt_cu_departmentName2.setText(cu_departmentName2);
        ImageView img_cu_call2 = vie.findViewById(R.id.img_cu_call2);
        ImageView img_cu_landline2 = vie.findViewById(R.id.img_cu_landline2);
        ImageView img_cu_mail2 = vie.findViewById(R.id.img_cu_mail2);
        ImageView img_cu_whatsapp2 = vie.findViewById(R.id.img_cu_whatsapp2);


        TextView txt_cu_departmentName3 = vie.findViewById(R.id.txt_acc);
//        txt_cu_departmentName3.setText(cu_departmentName3);
        ImageView img_cu_call3 = vie.findViewById(R.id.img_cu_call3);
        ImageView img_cu_landline3 = vie.findViewById(R.id.img_cu_landline3);
        ImageView img_cu_mail3 = vie.findViewById(R.id.img_cu_mail3);
        ImageView img_cu_whatsapp3 = vie.findViewById(R.id.img_cu_whatsapp3);

        dialog.setContentView(vie);
        dialog.setCancelable(true);
        dialog.show();


        img_cu_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_mobileNo1; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        img_cu_landline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_landline1; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        img_cu_mail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cu_email1, null));
                //i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                //i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);

            }
        });

        img_cu_whatsapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = cu_whatsapp1; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        img_cu_call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_mobileNo2; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        img_cu_landline2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_landline2; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        img_cu_mail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cu_email2, null));
                // i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                //i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);

            }
        });

        img_cu_whatsapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = cu_whatsapp2; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        img_cu_call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_mobileNo3; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        img_cu_landline3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_landline3; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        img_cu_mail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cu_email3, null));
                //i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                //i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);

            }
        });

        img_cu_whatsapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = cu_whatsapp3; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
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
        final lottiedialog lottie = new lottiedialog(this);
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
