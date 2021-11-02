package com.enfotrix.cgs.Fragments;

import static android.provider.Telephony.TextBasedSmsColumns.BODY;
import static android.provider.Telephony.TextBasedSmsColumns.SUBJECT;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enfotrix.cgs.Activities.ActivityAnnouncement;
import com.enfotrix.cgs.Activities.ActivityFee;
import com.enfotrix.cgs.Activities.ActivityFeedback;
import com.enfotrix.cgs.Activities.ActivityTimeTable;
import com.enfotrix.cgs.Activities.Activity_DateSheet;
import com.enfotrix.cgs.Lottiedialog;
import com.enfotrix.cgs.Models.HomeViewModel;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.SliderAdapterExample;
import com.enfotrix.cgs.SliderItem;
import com.enfotrix.cgs.Utils;
import com.enfotrix.cgs.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String latestsession;
    private TextView tv_status;
    private String contacttxt;
    private String txtsession, txtexamtype;
    private String status;

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


//        Toast.makeText(getContext(), "" + currentmonth, Toast.LENGTH_SHORT).show();

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
        fetchlastestsession();
        getcurrentmonthfee();

        return root;
    }

    private void getcurrentmonthfee() {

        final Lottiedialog lottiedialog = new Lottiedialog(getContext());
        lottiedialog.show();

        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        String currentmonth = dateFormat.format(date);

        db.collection("Sessions").orderBy("sessionID", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {

//                                Toast.makeText(getContext(), ""+document.getId(), Toast.LENGTH_SHORT).show();

                                db.collection("Fee").document(document.getId())
                                        .collection("Month").document(currentmonth)
                                        .collection("StudentsFee").document(utils.getToken())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    DocumentSnapshot document = task.getResult();
//
                                                    status = "";
                                                    status = document.getString("status");
//                                                    Toast.makeText(getContext(), "" + status, Toast.LENGTH_SHORT).show();


//                                                    if (document.getString("status") != null) {
//
//                                                        Toast.makeText(getContext(), "pendddd", Toast.LENGTH_SHORT).show();
//
//                                                        tv_status.setText(status);
//
//                                                    } else tv_status.setText("Pend");

                                                }
                                            }
                                        });

                            }

                            lottiedialog.dismiss();
                        }
                    }
                });


    }

    private void fetchlastestsession() {

        db.collection("Sessions").orderBy("sessionID", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //sesion =fd ;


                                latestsession = document.getId();

                                //fetchcurrentdaate(document.getId());


//
                                //Toast.makeText(getContext(), "Debug", Toast.LENGTH_SHORT).show();
//                                Toast.makeText(getContext(), "" + document.getId(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    }
                });

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
                Intent resultIntent = new Intent(getActivity().getApplicationContext(), Activity_DateSheet.class);
                resultIntent.putExtra("session", txtsession);
                resultIntent.putExtra("examtype", txtexamtype);
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

        AppCompatSpinner txtContact = attendancedialog.findViewById(R.id.txtContact);
        AppCompatButton btn_Contact = attendancedialog.findViewById(R.id.btn_Contact);


        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_department, departmentname);
//        txtContact.setText(arrayAdapter.getItem(0).toString(), false);
        txtContact.setAdapter(arrayAdapter);

        txtContact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                contacttxt = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomsheetcontact(contacttxt);

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

        final Lottiedialog lottie = new Lottiedialog(getContext());
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
                feedialog();
//                Intent intent = new Intent(getContext(), ActivityFee.class);
//                intent.putExtra("latestSession", latestsession);
//                startActivity(intent);
//                startActivity(new Intent(getContext(), ActivityFee.class));
//                Toast.makeText(getContext(), "Available Soon", Toast.LENGTH_SHORT).show();
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

    private void feedialog() {

        BottomSheetDialog dialog = new BottomSheetDialog(this.getContext());
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_fee, null);

        AppCompatButton btn_earlier = vie.findViewById(R.id.btn_earlier);
        TextView text_schoolName = vie.findViewById(R.id.text_schoolName);
//        TextView tv_date = vie.findViewById(R.id.tv_date);
        TextView tv_status = vie.findViewById(R.id.tv_stats);

        if (status != null) {
            tv_status.setText(status);
        } else tv_status.setText("pending");


        btn_earlier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View attendancedialog = getLayoutInflater().inflate(R.layout.fee_dialog, null);

                AutoCompleteTextView txtsession = attendancedialog.findViewById(R.id.txtsession);
                AutoCompleteTextView autotextmonth = attendancedialog.findViewById(R.id.autotextmonth);
                AppCompatButton btn_attendance = attendancedialog.findViewById(R.id.btn_attendance);

                // session spinner
                ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_attendancesession, sessions);
//                txtsession.setText(arrayAdapter.getItem(0).toString(), false);
                txtsession.setAdapter(arrayAdapter);

                // month spinner

                String montharray[] = {"January", "February", "March", "April", "May", "June", "July",
                        "August", "September", "October", "November", "December"};
                ArrayAdapter monthAdapter = new ArrayAdapter(getContext(), R.layout.dropdown_attendancesession, montharray);
//                txtsession.setText(arrayAdapter.getItem(0).toString(), false);
                autotextmonth.setAdapter(monthAdapter);

                btn_attendance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ActivityFee.class);
                        intent.putExtra("attendanceSession", txtsession.getText().toString());
                        intent.putExtra("month", autotextmonth.getText().toString());
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


}