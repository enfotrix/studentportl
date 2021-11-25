package com.enfotrix.cgs.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.enfotrix.cgs.Activities.ActivityAttendance;
import com.enfotrix.cgs.Activities.ActivityLogin;
import com.enfotrix.cgs.Activities.ActivityResult;
import com.enfotrix.cgs.Lottiedialog;
import com.enfotrix.cgs.Models.DashboardViewModel;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private String refUri;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    //------------------ variables initialization
    private TextView txt_studentRegNo, txt_studentFullName, txt_studentFatherName;
    private TextView tv_status, txt_studentmedium, txt_studentClass;
    private ImageView imageprofile, iv_logout, imgCalender;
    private RelativeLayout lay_logout, lay_result, lay_attendance;
    private FirebaseFirestore db;
    private Utils utils;
    private AutoCompleteTextView autoCompletetxt, text_examtype, txtattendance;
    private AppCompatButton btn_login, btn_attendance;

    private ArrayList<String> sessions, examtype;
    public String classid;
    private String classgrade;
    private String date;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String fromatedate;
    private String status = "";
    private String txtsession, txtexamtype, sessionstxt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getActivity().setTitle("Profile");


        /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        txt_studentRegNo = root.findViewById(R.id.txt_studentRegNo);
        txt_studentFullName = root.findViewById(R.id.txt_studentFullName);
        txt_studentFatherName = root.findViewById(R.id.txt_studentFatherName);
        txt_studentmedium = root.findViewById(R.id.txt_studentmedium);
        txt_studentClass = root.findViewById(R.id.txt_studentClass);
//        txt_studentAddress = root.findViewById(R.id.txt_studentAddress);
//        txt_studentPhoneNo = root.findViewById(R.id.txt_studentPhoneNo);
//        txt_studentDOB = root.findViewById(R.id.txt_studentDOB);
//        txt_studentEmail = root.findViewById(R.id.txt_studentEmail);
        imageprofile = root.findViewById(R.id.imageprofile);
        utils = new Utils(getContext());
        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
//        Toast.makeText(getContext(), "" + date, Toast.LENGTH_SHORT).show();


//        iv_logout = root.findViewById(R.id.iv_logout);
        lay_result = root.findViewById(R.id.lay_result);
//        btn_attendance = root.findViewById(R.id.btn_attendance);

        db = FirebaseFirestore.getInstance();
        utils = new Utils(getContext());

        sessions = new ArrayList<>();
        examtype = new ArrayList<>();

        lay_attendance = root.findViewById(R.id.lay_attendance);
        lay_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendancedialog();

            }
        });

        imageprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgProfile();

            }
        });

        lay_logout = root.findViewById(R.id.lay_logout);
        lay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();

            }
        });


        //------ progress report link click event
        lay_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultdialog();
//                startActivity(new Intent(getContext(), ActivityResult.class));
            }
        });

        //fetch data of specific student from db
        getData();
        fetchsession();
        fetchresult();
        getattendance();
        //fetchcurrentdaate();
        return root;
    }

    private void getattendance() {

        final Lottiedialog lottiedialog = new Lottiedialog(getContext());
        lottiedialog.show();

        ///////////////////////////////////////////////////
        db.collection("Sessions").orderBy("sessionID", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //sesion =fd ;

                                //sessions.add(document.getId());

                                if (classid != null) {

                                    db.collection("Attendance").document(document.getId())
                                            .collection("Date").document(date)
                                            .collection("Class").document(classid)
                                            .collection("Attende").document(utils.getToken())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        //Toast.makeText(getContext(), "debug", Toast.LENGTH_SHORT).show();
                                                        //Toast.makeText(ActivityAttendance.this, "Debug", Toast.LENGTH_SHORT).show();
                                                        DocumentSnapshot document = task.getResult();

                                                        status = document.getString("status");

                                                        //Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                }


                                //Toast.makeText(getContext(), "" + document.getId(), Toast.LENGTH_SHORT).show();
                            }

                            lottiedialog.dismiss();


                        }
                    }
                });

        /////////////////////////////////////////////////


    }

    private void imgProfile() {
        MaterialDialog mDialog = new MaterialDialog.Builder(this.getActivity())
                .setTitle("Upload")
                .setMessage("Are you sure want to Upload Image!")
                .setCancelable(false)
                .setPositiveButton("Upload", R.drawable.ic_baseline_file_upload_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {


                        chooseImage();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_baseline_cancel_presentation_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    private void fetchclasssection(String classid, String classgrade) {

        db.collection("Class").document(classgrade)
                .collection("Section").document(classid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            documentSnapshot.getString("class_medium");
                            documentSnapshot.getString("class_section");
                            documentSnapshot.getString("class_grade");


                            txt_studentClass.setText(documentSnapshot.getString("class_grade") + "-" + documentSnapshot.getString("class_section"));
                            txt_studentmedium.setText("" + documentSnapshot.getString("class_medium"));
                        }
                    }
                });
    }

    private void fetchcurrentdaate(String session) {

        // Toast.makeText(getContext(), "" + classid, Toast.LENGTH_SHORT).show();
       /* db.collection("Attendance").document(session)
                .collection("Date").document(date)
                .collection("Class").document(classid)
                .collection("Attende").document(utils.getToken())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            //Toast.makeText(ActivityAttendance.this, "Debug", Toast.LENGTH_SHORT).show();
                            DocumentSnapshot document = task.getResult();

                            status = document.getString("status");
                            if (status != null){

                            }
                                Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

    }

    public void logout() {
        MaterialDialog mDialog = new MaterialDialog.Builder(this.getActivity())
                .setTitle("Logout")
                .setMessage("Are you sure want to logout!")
                .setCancelable(false)
                .setPositiveButton("Logout", R.drawable.ic_baseline_logout_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        utils.logout();
                        startActivity(new Intent(getContext(), ActivityLogin.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("Cancel", R.drawable.ic_baseline_cancel_presentation_24, new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }


    private void fetchresult() {
        db.collection("ExamTypes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                examtype.add(document.getId());

                                //Toast.makeText(getContext(), "Debug", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "" + document.getId(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void fetchsession() {


        db.collection("Sessions").orderBy("sessionID", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //sesion =fd ;

                                sessions.add(document.getId());
                                //currentVar = document.getId();

                                //fetchcurrentdaate(document.getId());


//
                                //Toast.makeText(getContext(), "Debug", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "" + document.getId(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });
        //Toast.makeText(getContext(), ""+currentVar, Toast.LENGTH_SHORT).show();


        //fetchcurrentdaate();


//        db.collection("Exams").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if (queryDocumentSnapshots.isEmpty()) {
//                            Log.d("TAG", "onSuccess: LIST EMPTY");
//                            return;
//                        } else {
//
//
//                            Toast.makeText(getContext(), "" + queryDocumentSnapshots.toString(),
//                            Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull @NotNull Exception e) {
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

    }

    private void resultdialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = getLayoutInflater().inflate(R.layout.result_dialog, null);

        AppCompatSpinner spinner = view.findViewById(R.id.autoCompletetxt);
        AppCompatSpinner spinner1 = view.findViewById(R.id.text_examtype);
        btn_login = view.findViewById(R.id.btn_login);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_session, sessions);
//        autoCompletetxt.setText(arrayAdapter.getItem(0).toString(), false);
        spinner.setAdapter(arrayAdapter);

        ArrayAdapter Adapter = new ArrayAdapter(getContext(), R.layout.dropdown_examtype, examtype);
//        text_examtype.setText(Adapter.getItem(0).toString(), false);
        spinner1.setAdapter(Adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                txtsession = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                txtexamtype = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(getActivity().getApplicationContext(), ActivityResult.class);
                resultIntent.putExtra("session", txtsession);
                resultIntent.putExtra("examtype", txtexamtype);
                resultIntent.putExtra("classid", classid);
                resultIntent.putExtra("classgrade", classgrade);
//                getActivity().finish();
                startActivity(resultIntent);

//                Toast.makeText(getContext(), classid + "\n" + classgrade, Toast.LENGTH_SHORT).show();

            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void attendancedialog() {

        BottomSheetDialog dialog = new BottomSheetDialog(this.getContext());
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_attendance, null);

        AppCompatButton btn_earlier = vie.findViewById(R.id.btn_earlier);
        TextView text_schoolName = vie.findViewById(R.id.text_schoolName);
//        TextView tv_date = vie.findViewById(R.id.tv_date);
        tv_status = vie.findViewById(R.id.tv_stats);

        if (status != null) {
            tv_status.setText(status);
        } else tv_status.setText("N.A");

        btn_earlier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                startActivity(new Intent(getContext(), ActivityAttendance.class));

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View attendancedialog = getLayoutInflater().inflate(R.layout.attendance_dialog, null);

                AppCompatSpinner spinner = attendancedialog.findViewById(R.id.txtattendance);
                btn_attendance = attendancedialog.findViewById(R.id.btn_attendance);

                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_attendancesession, sessions);
//                txtattendance.setText(arrayAdapter.getItem(0).toString(), false);
                spinner.setAdapter(arrayAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        sessionstxt = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                btn_attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ActivityAttendance.class);
                        intent.putExtra("attendanceSession", sessionstxt);
                        intent.putExtra("classid", classid);
//                        getActivity().finish();
                        startActivity(intent);

                    }
                });

                builder.setView(attendancedialog);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        dialog.setContentView(vie);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getData() {
        final Lottiedialog lottie = new Lottiedialog(getContext());
        lottie.show();
        db.collection("Students").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        String student_RegNoFromDb = document.getString("student_regNo");
                        String student_FullNameFromDb = document.getString("student_fName");
                        String student_LastNameFromDb = document.getString("student_lName");
                        String student_FatherNameFromDb = document.getString("student_fatherName");
                        String student_homeAddressFromDb = document.getString("student_homeAddress");
                        String student_PhoneNoFromDb = document.getString("student_phoneNo");
                        String student_EmailFromDb = document.getString("student_email");
                        String student_DOBFromDb = document.getString("student_dob");
                        String student_profilePicFromDb = document.getString("student_picture");
                        Glide.with(getContext()).load(student_profilePicFromDb).into(imageprofile);

                        classid = document.getString("student_classID");
                        classgrade = document.getString("class_grade");

                        fetchclasssection(document.getString("student_classID"), document.getString("class_grade"));

                        txt_studentRegNo.setText(student_RegNoFromDb);
//                        txt_studentAddress.setText(student_homeAddressFromDb);
                        txt_studentFatherName.setText(student_FatherNameFromDb);
//                        txt_studentPhoneNo.setText(student_PhoneNoFromDb);
                        txt_studentFullName.setText(student_FullNameFromDb + " " + student_LastNameFromDb);
//                        txt_studentEmail.setText(student_EmailFromDb);
//                        txt_studentDOB.setText(student_DOBFromDb);
//                        Glide.with(imageprofile)
//                                .load(student_profilePicFromDb)
//                                .fitCenter().into(imageprofile);
                        lottie.dismiss();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        lottie.dismiss();
                    }
                });

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void updateUser(Uri filePath) {


        final Lottiedialog lottie = new Lottiedialog(getContext());
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();


        StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        ref.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Map<String, Object> m = new HashMap<>();
                                m.put("student_picture", uri.toString());


                                db.collection("Students").document(utils.getToken()).update(m)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                progressDialog.dismiss();
                                                lottie.dismiss();
                                                Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                                getData();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                lottie.dismiss();
                                                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        });


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //Toast.makeText(ActivityNominee.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                .getTotalByteCount());
                        progressDialog.setMessage("Uploading Data " + (int) progress + "%");
                    }
                });


    }


    /////////////////////////  IMAGE  //////////////////
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                imageprofile.setImageBitmap(bitmap);

                updateUser(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    ///////////////////////////////////////////////////////////////


}


