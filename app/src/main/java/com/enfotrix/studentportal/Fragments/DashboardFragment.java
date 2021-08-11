package com.enfotrix.studentportal.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.enfotrix.studentportal.Activities.ActivityAttendance;
import com.enfotrix.studentportal.Activities.ActivityFeedback;
import com.enfotrix.studentportal.Activities.ActivityLogin;
import com.enfotrix.studentportal.Activities.ActivityResult;
import com.enfotrix.studentportal.Models.DashboardViewModel;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.databinding.FragmentDashboardBinding;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    //------------------ variables initialization
    private TextView txt_studentRegNo, txt_studentFullName, txt_studentFatherName;
    private TextView txt_studentClass, tvSelectedDate;
    private ImageView imageView, iv_logout, imgCalender;
    private RelativeLayout lay_feedback, lay_result, lay_attendance;
    private FirebaseFirestore db;
    private Utils utils;
    private AutoCompleteTextView autoCompletetxt, text_examtype, txtattendance;
    private AppCompatButton btn_login, btn_attendance;

    private ArrayList<String> sessions, examtype;
    private String classid;
    private String classgrade;
    private String date;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String fromatedate;
    private String currentVar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        /*final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        txt_studentRegNo = root.findViewById(R.id.txt_studentRegNo);
        txt_studentFullName = root.findViewById(R.id.txt_studentFullName);
        txt_studentFatherName = root.findViewById(R.id.txt_studentFatherName);
//        txt_studentAddress = root.findViewById(R.id.txt_studentAddress);
//        txt_studentPhoneNo = root.findViewById(R.id.txt_studentPhoneNo);
//        txt_studentDOB = root.findViewById(R.id.txt_studentDOB);
//        txt_studentEmail = root.findViewById(R.id.txt_studentEmail);
        imageView = root.findViewById(R.id.imageView);
        utils = new Utils(getContext());
        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date = dateFormat.format(calendar.getTime());
//        Toast.makeText(getContext(), "" + date, Toast.LENGTH_SHORT).show();


        iv_logout = root.findViewById(R.id.iv_logout);
        lay_result = root.findViewById(R.id.lay_result);
//        btn_attendance = root.findViewById(R.id.btn_attendance);

        db = FirebaseFirestore.getInstance();

        utils = new Utils(getContext());

        lay_attendance = root.findViewById(R.id.lay_attendance);
        lay_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendancedialog();

            }
        });

        lay_feedback = root.findViewById(R.id.lay_feedback);
        lay_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityFeedback.class);
                startActivity(intent);
            }
        });

        //------ logout link click event
        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.logout();
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intent);
                getActivity().finish();
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

//        btn_attendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AttendanceSheet();
//            }
//        });

        //fetch data of specific student from db
        getData();
        fetchsession();
        fetchresult();
        //fetchcurrentdaate();
        return root;
    }

    private void fetchcurrentdaate(String session) {

        //Toast.makeText(getContext(), "" + session, Toast.LENGTH_SHORT).show();
        db.collection("Attendance").document(session)
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
                            String status = "";
                            status = document.getString("status");
                            if (status != null)
                                Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void fetchresult() {
        db.collection("ExamTypes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            examtype = new ArrayList<>();
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

                            sessions = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //sesion =fd ;

                                sessions.add(document.getId());
                                //currentVar = document.getId();

                                fetchcurrentdaate(document.getId());


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

        autoCompletetxt = view.findViewById(R.id.autoCompletetxt);
        text_examtype = view.findViewById(R.id.text_examtype);
        btn_login = view.findViewById(R.id.btn_login);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_session, sessions);
        autoCompletetxt.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompletetxt.setAdapter(arrayAdapter);

        ArrayAdapter Adapter = new ArrayAdapter(getContext(), R.layout.dropdown_examtype, examtype);
        text_examtype.setText(Adapter.getItem(0).toString(), false);
        text_examtype.setAdapter(Adapter);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(getActivity().getApplicationContext(), ActivityResult.class);
                resultIntent.putExtra("session", autoCompletetxt.getText().toString());
                resultIntent.putExtra("examtype", text_examtype.getText().toString());
                resultIntent.putExtra("classid", classid);
                resultIntent.putExtra("classgrade", classgrade);
                getActivity().finish();
                startActivity(resultIntent);
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
        TextView tv_date = vie.findViewById(R.id.tv_date);
        TextView tv_status = vie.findViewById(R.id.tv_status);


        btn_earlier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                startActivity(new Intent(getContext(), ActivityAttendance.class));

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View attendancedialog = getLayoutInflater().inflate(R.layout.attendance_dialog, null);

                txtattendance = attendancedialog.findViewById(R.id.txtattendance);
                btn_attendance = attendancedialog.findViewById(R.id.btn_attendance);
                imgCalender = attendancedialog.findViewById(R.id.imgCalender);
                tvSelectedDate = attendancedialog.findViewById(R.id.tvSelectedDate);

                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_attendancesession, sessions);
                txtattendance.setText(arrayAdapter.getItem(0).toString(), false);
                txtattendance.setAdapter(arrayAdapter);


                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }

                };

                imgCalender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(getContext(), date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                btn_attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ActivityAttendance.class);
                        intent.putExtra("attendanceSession", txtattendance.getText().toString());
                        intent.putExtra("date", fromatedate);
                        intent.putExtra("classid", classid);
                        getActivity().finish();
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

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        fromatedate = sdf.format(calendar.getTime());

        tvSelectedDate.setText(sdf.format(calendar.getTime()));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getData() {
        final lottiedialog lottie = new lottiedialog(getContext());
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
                        String student_profilePicFromDb = document.getString("student_profilePic");

                        classid = document.getString("student_classID");
                        classgrade = document.getString("class_grade");

                        txt_studentRegNo.setText(student_RegNoFromDb);
//                        txt_studentAddress.setText(student_homeAddressFromDb);
                        txt_studentFatherName.setText(student_FatherNameFromDb);
//                        txt_studentPhoneNo.setText(student_PhoneNoFromDb);
                        txt_studentFullName.setText(student_FullNameFromDb + " " + student_LastNameFromDb);
//                        txt_studentEmail.setText(student_EmailFromDb);
//                        txt_studentDOB.setText(student_DOBFromDb);
                        Glide.with(imageView)
                                .load(student_profilePicFromDb)
                                .fitCenter().into(imageView);
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
}


