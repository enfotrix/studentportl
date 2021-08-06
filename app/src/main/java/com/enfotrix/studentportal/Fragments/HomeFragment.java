package com.enfotrix.studentportal.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.enfotrix.studentportal.Activities.ActivityAnnouncement;
import com.enfotrix.studentportal.Activities.ActivityResult;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {


    private Utils utils;
    private FirebaseFirestore firestore;
    private FirebaseFirestore db;

    private Button btn_announcement, btn_contactus, btn_feedback, btn_gallery;


    RelativeLayout lay_result, lay_dateSheet, lay_tiemtable;

    private SliderView sliderView;
    private SliderAdapterExample adapterSlider;

    private ImageView iv_announcement, im_contactus, im_settings;


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
        db = FirebaseFirestore.getInstance();
        utils = new Utils(this.getContext());

        //ini views
        IniViews(root);


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


        fetchContacts();


        return root;
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

        lay_result = root.findViewById(R.id.lay_result);
        lay_dateSheet = root.findViewById(R.id.lay_dateSheet);
        lay_tiemtable = root.findViewById(R.id.lay_tiemtable);

        im_settings = root.findViewById(R.id.im_settings);
        iv_announcement = root.findViewById(R.id.iv_announcement);
        im_contactus = root.findViewById(R.id.im_contactus);

        sliderView = root.findViewById(R.id.imageSlider);

        lay_result.setOnClickListener(this);
        lay_tiemtable.setOnClickListener(this);
        lay_dateSheet.setOnClickListener(this);
        im_settings.setOnClickListener(this);
        iv_announcement.setOnClickListener(this);
        im_contactus.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void contactUs() {


        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View vie = getLayoutInflater().inflate(R.layout.bottam_sheet_contacts, null);


        TextView txt_cu_departmentName1 = vie.findViewById(R.id.txt_pri);
        txt_cu_departmentName1.setText(cu_departmentName1);
        ImageView img_cu_call1 = vie.findViewById(R.id.img_cu_call1);
        ImageView img_cu_landline1 = vie.findViewById(R.id.img_cu_landline1);
        ImageView img_cu_mail1 = vie.findViewById(R.id.img_cu_mail1);
        ImageView img_cu_whatsapp1 = vie.findViewById(R.id.img_cu_whatsapp1);

        TextView txt_cu_departmentName2 = vie.findViewById(R.id.txt_admin);
        txt_cu_departmentName2.setText(cu_departmentName2);
        ImageView img_cu_call2 = vie.findViewById(R.id.img_cu_call2);
        ImageView img_cu_landline2 = vie.findViewById(R.id.img_cu_landline2);
        ImageView img_cu_mail2 = vie.findViewById(R.id.img_cu_mail2);
        ImageView img_cu_whatsapp2 = vie.findViewById(R.id.img_cu_whatsapp2);


        TextView txt_cu_departmentName3 = vie.findViewById(R.id.txt_acc);
        txt_cu_departmentName3.setText(cu_departmentName3);
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
            case R.id.im_contactus:
                contactUs();
                break;
            case R.id.iv_announcement:
                startActivity(new Intent(getContext(), ActivityAnnouncement.class));
                break;
            case R.id.im_settings:

                break;
            case R.id.lay_result:
                startActivity(new Intent(getContext(), ActivityResult.class));
                break;
            case R.id.lay_tiemtable:
                startActivity(new Intent(getContext(), ActivityTimeTable.class));
                break;
            case R.id.lay_dateSheet:
                startActivity(new Intent(getContext(), Activity_DateSheet.class));
                break;
            default:
                break;
        }
    }
}