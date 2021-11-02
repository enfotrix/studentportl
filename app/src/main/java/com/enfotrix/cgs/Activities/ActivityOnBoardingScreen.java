package com.enfotrix.cgs.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.enfotrix.cgs.OnBoardAdapter;
import com.enfotrix.cgs.OnBoardModel;
import com.enfotrix.cgs.R;
import com.enfotrix.cgs.Lottiedialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ActivityOnBoardingScreen extends AppCompatActivity {

    private FirebaseFirestore db;
    private String principalmsg, directormsg;
    ViewPager2 viewPager;
    LinearLayout lay_dots;
    private String dimg;
    private OnBoardAdapter onBoardAdapter;
    ArrayList<OnBoardModel> boardModelArrayList = new ArrayList<>();
    AppCompatButton btn_main;
    private String pimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding_screen);

        // hide actionbar and statusBar
        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();

        viewPager = findViewById(R.id.viewPager);
        lay_dots = findViewById(R.id.lay_dots);
        btn_main = findViewById(R.id.btn_main);

        Animation animationUtils = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        btn_main.startAnimation(animationUtils);

        getmsg();

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewPager.getCurrentItem() + 1 < onBoardAdapter.getItemCount()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });


    }

    private void getmsg() {

        final Lottiedialog lottiedialog = new Lottiedialog(this);
        lottiedialog.show();

        boardModelArrayList.clear();

        db.collection("Message")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        if (document.getString("status").equals("principal")) {

                            principalmsg = document.getString("msg");
                            pimg = document.getString("photo");


                            //getDataForonBoarding(principalmsg)

                            // Toast.makeText(ActivityOnBoardingScreen.this, "" + document.getString("msg"), Toast.LENGTH_SHORT).show();
                        }

                        if (document.getString("status").equals("director")) {

                            directormsg = document.getString("msg");
                            dimg = document.getString("photo");

                            //  Toast.makeText(ActivityOnBoardingScreen.this, "" + document.getString("msg"), Toast.LENGTH_SHORT).show();

                        }
                        // sliderAdapter.getmsg(principalmsg, directormsg);
                    }

                    OnBoardModel dmsg = new OnBoardModel();
                    dmsg.setTitle("Director Message");
                    dmsg.setImg(dimg);
                    dmsg.setName("Ch Ikram Ullah Warraich, Chairman");
                    dmsg.setMsg(directormsg);
                    boardModelArrayList.add(dmsg);

                    OnBoardModel pmsg = new OnBoardModel();
                    pmsg.setTitle("Principal Message");
                    pmsg.setImg(pimg);
                    pmsg.setName("Principal Malik Mumtaz Hussain Khichi");
                    pmsg.setMsg(principalmsg);
                    boardModelArrayList.add(pmsg);

                    onBoardAdapter = new OnBoardAdapter(getApplicationContext(), boardModelArrayList);
                    viewPager.setAdapter(onBoardAdapter);
                    onBoardAdapter.notifyDataSetChanged();

                    setindicator();
                    activeindicator(0);

                    viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            super.onPageSelected(position);
                            activeindicator(position);
                        }
                    });

                    lottiedialog.dismiss();

                }

            }

        });

    }

    private void setindicator() {
        ImageView[] incators = new ImageView[onBoardAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < incators.length; i++) {
            incators[i] = new ImageView(getApplicationContext());
            incators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext()
                    , R.drawable.inactive_indicator));
            incators[i].setLayoutParams(layoutParams);
            lay_dots.addView(incators[i]);
        }
    }

    private void activeindicator(int index) {
        int childcount = lay_dots.getChildCount();
        for (int i = 0; i < childcount; i++) {
            ImageView imageView = (ImageView) lay_dots.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(), R.drawable.active_indicator
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(), R.drawable.inactive_indicator
                ));
            }
        }

        if (index == onBoardAdapter.getItemCount() - 1) {
            btn_main.setText("start");
        } else {
            btn_main.setText("Next");
        }
    }
}