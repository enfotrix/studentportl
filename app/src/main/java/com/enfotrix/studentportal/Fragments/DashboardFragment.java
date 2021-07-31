package com.enfotrix.studentportal.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enfotrix.studentportal.Activities.ActivityLogin;
import com.enfotrix.studentportal.Models.DashboardViewModel;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;


    //------------------ variables initialization
    private TextView txt_studentRegNo, txt_studentFullName, txt_studentFatherName;
    private TextView txt_studentAddress, txt_studentPhoneNo;
    private TextView txt_studentEmail, txt_studentDOB;
    private Button btn_logout, btn_attendance;
    private FirebaseFirestore db;
    private Utils utils;


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

//        txt_studentRegNo = root.findViewById(R.id.txt_studentRegNo);
//        txt_studentFullName = root.findViewById(R.id.txt_studentFullName);
//        txt_studentFatherName = root.findViewById(R.id.txt_studentFatherName);
//        txt_studentAddress = root.findViewById(R.id.txt_studentAddress);
//        txt_studentPhoneNo = root.findViewById(R.id.txt_studentPhoneNo);
//        txt_studentDOB = root.findViewById(R.id.txt_studentDOB);
//        txt_studentEmail = root.findViewById(R.id.txt_studentEmail);
        utils = new Utils(getContext());

//        btn_logout = root.findViewById(R.id.btn_logout);
//        btn_attendance = root.findViewById(R.id.btn_attendance);

        db = FirebaseFirestore.getInstance();


        //------ logout button click event
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.logout();
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendanceSheet();
            }
        });

        //fetch data of specific student from db
        getData();

//        db.collection("Students").whereEqualTo("student_regNo",utils.getToken()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document : task.getResult()) {
//                        //String str = document.getString("student_fullName");
//                        String student_RegNoFromDb=document.getString("student_regNo");
//                        String student_FullNameFromDb = document.getString("student_fName");
//                        String student_FatherNameFromDb = document.getString("student_fatherName");
//                        String student_homeAddressFromDb = document.getString("student_homeAddress");
//                        String student_PhoneNoFromDb = document.getString("student_phoneNo");
//                        String student_EmailFromDb = document.getString("student_email");
//                        String student_DOBFromDb = document.getString("student_dob");
//
//
//                        txt_studentRegNo.setText(student_RegNoFromDb);
//                        txt_studentAddress.setText(student_homeAddressFromDb);
//                        txt_studentFatherName.setText(student_FatherNameFromDb);
//                        txt_studentPhoneNo.setText(student_PhoneNoFromDb);
//                        txt_studentFullName.setText(student_FullNameFromDb);
//                        txt_studentEmail.setText(student_EmailFromDb);
//                        txt_studentDOB.setText(student_DOBFromDb);
//
//
////                    }
//
//
//
//
//                }
//
//            }
//        });


        return root;
    }

    private void AttendanceSheet() {

        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_attendance, null);


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
        db.collection("Students").document(utils.getToken()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();

                        String student_RegNoFromDb = document.getString("student_regNo");
                        String student_FullNameFromDb = document.getString("student_fName");
                        String student_FatherNameFromDb = document.getString("student_fatherName");
                        String student_homeAddressFromDb = document.getString("student_homeAddress");
                        String student_PhoneNoFromDb = document.getString("student_phoneNo");
                        String student_EmailFromDb = document.getString("student_email");
                        String student_DOBFromDb = document.getString("student_dob");


                        txt_studentRegNo.setText(student_RegNoFromDb);
                        txt_studentAddress.setText(student_homeAddressFromDb);
                        txt_studentFatherName.setText(student_FatherNameFromDb);
                        txt_studentPhoneNo.setText(student_PhoneNoFromDb);
                        txt_studentFullName.setText(student_FullNameFromDb);
                        txt_studentEmail.setText(student_EmailFromDb);
                        txt_studentDOB.setText(student_DOBFromDb);


                    }
                });
    }
}

