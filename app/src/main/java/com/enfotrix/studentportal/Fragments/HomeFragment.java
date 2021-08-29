package com.enfotrix.studentportal.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enfotrix.studentportal.Activities.ActivityAnnouncement;
import com.enfotrix.studentportal.Activities.ActivityFeedback;
import com.enfotrix.studentportal.Activities.ActivityTimeTable;
import com.enfotrix.studentportal.Activities.Activity_DateSheet;
import com.enfotrix.studentportal.Models.HomeViewModel;
import com.enfotrix.studentportal.R;
import com.enfotrix.studentportal.SliderAdapterExample;
import com.enfotrix.studentportal.SliderItem;
import com.enfotrix.studentportal.Utils;
import com.enfotrix.studentportal.databinding.FragmentHomeBinding;
import com.enfotrix.studentportal.lottiedialog;
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
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.provider.Telephony.TextBasedSmsColumns.BODY;
import static android.provider.Telephony.TextBasedSmsColumns.SUBJECT;

public class HomeFragment extends Fragment implements View.OnClickListener {





    private Utils utils;
    private FirebaseFirestore firestore;
    private FirebaseFirestore db;

    private ArrayList<String> sessions;
    private ArrayList<String> examtype;

    private Button btn_announcement, btn_contactus, btn_feedback, btn_gallery;


    RelativeLayout lay_settings, lay_dateSheet, lay_feedback,
            lay_tiemtable, lay_ann, lay_contactus;

    private SliderView sliderView;
    private SliderAdapterExample adapterSlider;

    private ImageView iv_announcement, im_contactus, im_settings;


    private String tittle, cu_email1, cu_mobileNo1, cu_whatsapp1, cu_landline1;
    private String cu_departmentName2, cu_email2, cu_mobileNo2, cu_whatsapp2, cu_landline2;
    private String cu_departmentName3, cu_email3, cu_mobileNo3, cu_whatsapp3, cu_landline3;


    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ArrayList<String> departmentname;
    private String contact_num, contact_email, contact_landline, contact_whatsapp;
    private AutoCompleteTextView autoCompletetxt, text_examtype;
    AppCompatButton btn_login;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        firestore = FirebaseFirestore.getInstance();
        db = FirebaseFirestore.getInstance();
        utils = new Utils(this.getContext());
        departmentname = new ArrayList<>();


        //ini views
        IniViews(root);

        sessions = new ArrayList<>();
        examtype = new ArrayList<>();

        // sildercall
        getSliderImage();

//        sliderView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), ActivityGallery.class));
//            }
//        });

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


//        fetchContacts();
        fetchdepartment();
        fetchsession();
        fetchresult();

        return root;
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
                Intent resultIntent = new Intent(getActivity().getApplicationContext(), Activity_DateSheet.class);
                resultIntent.putExtra("session", autoCompletetxt.getText().toString());
                resultIntent.putExtra("examtype", text_examtype.getText().toString());
//                resultIntent.putExtra("classid", classid);
//                resultIntent.putExtra("classgrade", classgrade);
//                getActivity().finish();
                startActivity(resultIntent);
            }
        });

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();


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

    private void fetchdepartment() {

        db.collection("ContactUs").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                departmentname.add(document.getId());

                                // Toast.makeText(getContext(), ""+departmentname, Toast.LENGTH_SHORT).show();

                                //Toast.makeText(getContext(), "Debug", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "" + document.getId(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
    }

    private void getSliderImage() {

        adapterSlider = new SliderAdapterExample(this.getContext());

        sliderView.setSliderAdapter(adapterSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();

        List<SliderItem> sliderItemList = new ArrayList<>();

        db.collection("Gallery").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                SliderItem sliderItem = new SliderItem();
                                sliderItem.setImageUrl(document.getString("path"));
//                                Model_Image model_image = new Model_Image(
//                                        document.getId(),
//                                        document.getString("path")
//                                );
                                sliderItemList.add(sliderItem);
                            }

                            adapterSlider.renewItems(sliderItemList);

//                            Adapter_Image adapter_image = new Adapter_Image(ActivityGallery.this, list);
//                            recyclerView.setAdapter(adapter_image);
                        }
                    }
                });

    }

    private void IniViews(View root) {

//        lay_result = root.findViewById(R.id.lay_result);
        lay_dateSheet = root.findViewById(R.id.lay_dateSheet);
        lay_tiemtable = root.findViewById(R.id.lay_tiemtable);
        lay_feedback = root.findViewById(R.id.lay_feedback);

        lay_contactus = root.findViewById(R.id.lay_contactus);
        lay_ann = root.findViewById(R.id.lay_ann);
        lay_settings = root.findViewById(R.id.lay_settings);

        sliderView = root.findViewById(R.id.imageSlider);

//        lay_result.setOnClickListener(this);
        lay_tiemtable.setOnClickListener(this);
        lay_feedback.setOnClickListener(this);
        lay_dateSheet.setOnClickListener(this);
        lay_settings.setOnClickListener(this);
        lay_ann.setOnClickListener(this);
        lay_contactus.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void contactUs() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View attendancedialog = getLayoutInflater().inflate(R.layout.department_dialog, null);

        AutoCompleteTextView txtContact = attendancedialog.findViewById(R.id.txtContact);
        AppCompatButton btn_Contact = attendancedialog.findViewById(R.id.btn_Contact);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_department, departmentname);
        txtContact.setText(arrayAdapter.getItem(0).toString(), false);
        txtContact.setAdapter(arrayAdapter);

        btn_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheetcontact(txtContact.getText().toString().trim());

            }
        });

        builder.setView(attendancedialog);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void bottomsheetcontact(String dptName) {

        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
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
                            cu_whatsapp1 = documentSnapshot.getString("whatsapp");
                            cu_landline1 = documentSnapshot.getString("landline");
                            tittle = documentSnapshot.getString("tittle");
                            txt_cu_departmentName1.setText(tittle);

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
                i.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                i.putExtra(Intent.EXTRA_TEXT, BODY);
                startActivity(i);
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

                                    // cu_departmentName1 = document.getString("cu_departmentName");
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
                        Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lay_contactus:
                contactUs();
                break;
            case R.id.lay_ann:
                startActivity(new Intent(getContext(), ActivityAnnouncement.class));
                break;
            case R.id.lay_settings:
                Toast.makeText(getContext(), "Available Soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lay_result:
//                startActivity(new Intent(getContext(), ActivityResult.class));
                break;
            case R.id.lay_feedback:
                startActivity(new Intent(getContext(), ActivityFeedback.class));
                break;
            case R.id.lay_tiemtable:
                startActivity(new Intent(getContext(), ActivityTimeTable.class));
                break;
            case R.id.lay_dateSheet:
                resultdialog();
//                startActivity(new Intent(getContext(), Activity_DateSheet.class));
                break;
            default:
                break;
        }
    }




}