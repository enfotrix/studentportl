package com.enfotrix.studentportal.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enfotrix.studentportal.Activities.ActivityAnnouncement;
import com.enfotrix.studentportal.Activities.ActivityFeedback;
import com.enfotrix.studentportal.Activities.ActivityGallery;
import com.enfotrix.studentportal.Models.HomeViewModel;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.databinding.FragmentHomeBinding;
import com.enfotrix.studentportal.lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeFragment extends Fragment {


    private Utils utils;
    private FirebaseFirestore firestore;

    private Button btn_announcement, btn_contactus, btn_feedback, btn_gallery;

    private CardView cv_contactus;
    RelativeLayout lay_gallery, lay_announcement, lay_feedback;


    private String cu_departmentName1, cu_email1, cu_mobileNo1, cu_whatsapp1, cu_landline1;
    private String cu_departmentName2, cu_email2, cu_mobileNo2, cu_whatsapp2, cu_landline2;
    private String cu_departmentName3, cu_email3, cu_mobileNo3, cu_whatsapp3, cu_landline3;


    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        firestore = FirebaseFirestore.getInstance();
        utils = new Utils(this.getContext());

        cv_contactus = root.findViewById(R.id.cv_contactus);
        cv_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactUs();
            }
        });

        lay_gallery = root.findViewById(R.id.lay_gallery);
        lay_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityGallery.class));
            }
        });

        lay_feedback = root.findViewById(R.id.lay_feedback);
        lay_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityFeedback.class));
            }
        });

        lay_announcement = root.findViewById(R.id.lay_announcement);
        lay_announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityAnnouncement.class));
            }
        });

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        btn_announcement=root.findViewById(R.id.btn_announcement);
//        btn_announcement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ActivityAnnouncement.class));
//            }
//        });


        fetchContacts();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void contactUs() {


        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_contacts, null);


//        TextView txt_cu_departmentName1 = vie.findViewById(R.id.txt_cu_departmentName1);
//        txt_cu_departmentName1.setText(cu_departmentName1);
//        Button btn_cu_call1 = vie.findViewById(R.id.btn_cu_call1);
//        Button btn_cu_mail1 = vie.findViewById(R.id.btn_cu_mail1);
//        Button btn_cu_whatsapp1 = vie.findViewById(R.id.btn_cu_whatsapp1);
//
//        TextView txt_cu_departmentName2 = vie.findViewById(R.id.txt_cu_departmentName2);
//        txt_cu_departmentName2.setText(cu_departmentName2);
//        Button btn_cu_call2 = vie.findViewById(R.id.btn_cu_call2);
//        Button btn_cu_mail2 = vie.findViewById(R.id.btn_cu_mail2);
//        Button btn_cu_whatsapp2 = vie.findViewById(R.id.btn_cu_whatsapp2);
//
//
//        TextView txt_cu_departmentName3 = vie.findViewById(R.id.txt_cu_departmentName3);
//        txt_cu_departmentName3.setText(cu_departmentName3);
//        Button btn_cu_call3 = vie.findViewById(R.id.btn_cu_call3);
//        Button btn_cu_mail3 = vie.findViewById(R.id.btn_cu_mail3);
//        Button btn_cu_whatsapp3 = vie.findViewById(R.id.btn_cu_whatsapp3);

        dialog.setContentView(vie);
        dialog.setCancelable(true);
        dialog.show();


        /*btn_cu_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_mobileNo1; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        btn_cu_mail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cu_email1, null));
                i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);

            }
        });

        btn_cu_whatsapp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = cu_whatsapp1; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btn_cu_call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_mobileNo2; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        btn_cu_mail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cu_email2, null));
                i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);

            }
        });

        btn_cu_whatsapp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = cu_whatsapp2; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        btn_cu_call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = cu_mobileNo3; // use country code with your phone number
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + contact));
                startActivity(i);
            }
        });

        btn_cu_mail3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", cu_email3, null));
                i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);

            }
        });

        btn_cu_whatsapp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String contact = cu_whatsapp3; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });*/

    }


    private void fetchContacts() {

        final lottiedialog lottie = new lottiedialog(getContext());
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

                                if (document.getString("cu_departmentName").equals("Finance")) {

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
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}